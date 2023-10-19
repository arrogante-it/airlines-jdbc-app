package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_AIRPLANE;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW_CREW_MEMBER;
import static com.airlines.jdbc.app.constants.AirlinesTestConstants.CREATE_CREW_MEMBER;
import com.airlines.jdbc.app.exception.SQLOperationException;
import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;
import static com.airlines.jdbc.app.persistance.entities.AirplaneModel.BOEING;
import com.airlines.jdbc.app.jdbcUtil.JdbcUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class AirplaneDaoTest {
    private static final LocalDate DATE = LocalDate.parse("2023-09-12");

    private static AirplaneDAO airplaneDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        DataSource h2DataSource = JdbcUtil.createDefaultInMemoryH2DataSource();
        createAccountTable(h2DataSource);
        airplaneDAO = new AirplaneDAOImpl(h2DataSource);
    }

    private static void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(CREATE_CREW
                    + CREATE_AIRPLANE
                    + CREATE_CREW_MEMBER
                    + CREATE_CREW_CREW_MEMBER + "insert into crew (crew_name) values ('Fight Club')"
            );
        }
    }

    @Test
    public void shouldCorrectlySaveAirplane() {
        Airplane airplane = getAirplaneInstance("RTE543", 1L, "Fight Club");

        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size();
        airplaneDAO.saveAirplane(airplane);
        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertNotNull(airplane.getId());
        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyThrowsException() {
        Airplane airplane = getAirplaneInstance("RTE543", 200L, "Fight Club");

        String expectedErrorMessage = String.format("%1s with id = %2d",
                "Can't insert into DB ",
                airplane.getId());

        SQLOperationException exception =
                assertThrows(SQLOperationException.class, () -> airplaneDAO.saveAirplane(airplane));

        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void shouldCorrectlyFindAirplaneByCode() {
        String code = "RTE543";
        Airplane airplane = getAirplaneInstance(code, 1L, "Fight Club");

        airplaneDAO.saveAirplane(airplane);
        Airplane foundAirplane = airplaneDAO.findAirplaneByCode(code);

        assertNotNull(foundAirplane.getId());
        assertEquals(foundAirplane, airplane);
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
    public void shouldCorrectlyFindAirplaneByCrewName() {
        String crewName = "Fight Club";
        Airplane airplane = getAirplaneInstance("RTE543", 1L, crewName);

        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size();
        airplaneDAO.saveAirplane(airplane);
        List<Airplane> airplanes = airplaneDAO.searchAirplanesByCrewName(crewName);

        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyDeleteAirplane() {
        String crewName = "Fight Club";
        Airplane airplane = getAirplaneInstance("RTE543", 1L, crewName);

        airplaneDAO.saveAirplane(airplane);
        List<Airplane> airplanesBefore = airplaneDAO.findAllAirplanes(); // 1 or more

        airplaneDAO.deleteAirplane(airplane);

        List<Airplane> airplanesAfter = airplaneDAO.findAllAirplanes();

        assertEquals(airplanesAfter.size(),  airplanesBefore.size() - 1);
        assertFalse(airplanesAfter.contains(airplane));
    }

    private Airplane getAirplaneInstance(String codeName, Long crewId, String crewName) {
        Crew crew = new Crew();
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
}
