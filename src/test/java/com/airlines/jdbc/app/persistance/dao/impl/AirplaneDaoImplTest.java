package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.FileReader;
import com.airlines.jdbc.app.TestDataSourceProvider;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_AIRPLANE;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW_CREW_MEMBER;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW_MEMBER;
import com.airlines.jdbc.app.persistance.dao.AirplaneDao;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;
import static com.airlines.jdbc.app.persistance.entities.Model.AIRBUS;
import static com.airlines.jdbc.app.persistance.entities.Model.BOEING;
import static com.airlines.jdbc.app.persistance.entities.Model.BOMBARDIER;
import com.airlines.jdbc.app.persistance.exception.SqlOperationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class AirplaneDaoImplTest {
    private static final LocalDate DATE = LocalDate.parse("2023-09-12");

    private static AirplaneDao airplaneDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        //createAirplaneTable(h2DataSource);
        createTable(h2DataSource);
        populateTable(h2DataSource);
        airplaneDAO = new AirplaneDaoImpl(h2DataSource);
    }

    @Test
    public void shouldCorrectlySaveAirplane() {
        Airplane airplane = getAirplaneInstance("RTE543", 1L, "Grey Crows");
        int expectedSize = airplaneDAO.findAllAirplanes().size();

        airplaneDAO.saveAirplane(airplane);

        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertNotNull(airplane.getId());
        assertEquals(expectedSize + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyThrowsException() {
        Airplane airplane = getAirplaneInstance("RTE543", 200L, "Fight Club");
        String expectedErrorMessage = String.format("%1s with id = %2d", "Can't insert into DB ", airplane.getId());

        SqlOperationException exception =
                assertThrows(SqlOperationException.class, () -> airplaneDAO.saveAirplane(airplane));

        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void shouldCorrectlyFindAirplaneByCode() {
        String code = "RTE543";
        Airplane expected = getAirplaneInstance(code, 1L, "Fight Club");
        airplaneDAO.saveAirplane(expected);

        Airplane foundAirplane = airplaneDAO.findAirplaneByCode(code);

        assertNotNull(foundAirplane.getId());
        assertEquals(expected, foundAirplane);
    }

    @Test
    public void shouldCorrectlyFindAllAirplanes() {
        Airplane airplane = getAirplaneInstance("RTE543", 1L, "Fight Club");
        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size();

        airplaneDAO.saveAirplane(airplane);

        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyFindAirplanesByCrewName() {
        String crewName = "Fight Club";
        Airplane airplane = getAirplaneInstance("RTE543", 1L, crewName);
        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size();

        airplaneDAO.saveAirplane(airplane);

        List<Airplane> airplanes = airplaneDAO.searchAirplanesByCrewName(crewName);

        assertNotNull(airplanes.get(0).getId());
        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyDeleteAirplane() {
        String crewName = "Grey Crows";
        Airplane airplane = getAirplaneInstance("RTE543", 1L, crewName);
        airplaneDAO.saveAirplane(airplane);
        List<Airplane> airplanesBefore = airplaneDAO.findAllAirplanes();

        airplaneDAO.deleteAirplane(airplane);

        List<Airplane> airplanesAfter = airplaneDAO.findAllAirplanes();

        assertEquals(airplanesAfter.size(), airplanesBefore.size() - 1);
        assertFalse(airplanesAfter.contains(airplane));
    }

    @Test
    public void shouldCorrectlyUpdateAirplaneAndSetCrewId() {
        Crew crew1 = new Crew.Builder().build();
        crew1.setId(1L);
        crew1.setName("Fight Club");
        Airplane airplane1 = new Airplane.Builder()
                .codeName("ZXC322")
                .model(BOMBARDIER)
                .manufactureDate(LocalDate.now())
                .capacity(999)
                .flightRange(7000)
                .crew(crew1)
                .build();
        Crew crew2 = new Crew.Builder().build();
        crew2.setId(2L);
        crew2.setName("Grey Crows");
        Airplane expected = new Airplane.Builder()
                .id(1L)
                .codeName("QWE321")
                .model(AIRBUS)
                .manufactureDate(LocalDate.now())
                .capacity(777)
                .flightRange(9000)
                .crew(crew2)
                .build();
        airplaneDAO.saveAirplane(airplane1);

        airplaneDAO.updateAirplaneAndSetCrewId(expected, crew2);

        Airplane actual = airplaneDAO.findAllAirplanes().iterator().next();

        assertEquals(expected, actual);
        assertEquals(airplaneDAO.findAllAirplanes().get(0).getCodeName(), expected.getCodeName());
        assertEquals(airplaneDAO.findAllAirplanes().get(0).getCrew().getId(), crew2.getId());
    }

    private static void createAirplaneTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(CREATE_CREW
                    + CREATE_AIRPLANE
                    + CREATE_CREW_MEMBER
                    + CREATE_CREW_CREW_MEMBER + "insert into crew (crew_name) values ('Fight Club');"
                    + "insert into crew (crew_name) values ('Grey Crows');"
            );
        }
    }

    private Airplane getAirplaneInstance(String codeName, Long crewId, String crewName) {
        Crew crew = new Crew.Builder().build();
        crew.setId(crewId);
        crew.setName(crewName);

        return new Airplane.Builder()
                .codeName(codeName)
                .model(BOEING)
                .manufactureDate(DATE)
                .capacity(450)
                .flightRange(3000)
                .crew(crew)
                .build();
    }

    static void createTable(DataSource dataSource) throws SQLException {
        String createTablesSql = FileReader.readWholeFileFromResources("/create_tables.sql");

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            statement.close();
        }
    }

    static void populateTable(DataSource dataSource) throws SQLException {
        String createTablesSql = FileReader.readWholeFileFromResources("/populate.sql");

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            statement.close();
        }
    }
}
