package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.InputUtils;
import com.airlines.jdbc.app.TestDataSourceProvider;
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
import java.time.LocalDate;
import java.util.List;

public class AirplaneDaoImplTest {
    private final LocalDate DATE = LocalDate.parse("2023-09-12");

    private AirplaneDao airplaneDAO;

    @BeforeEach
    public void setUp() {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        InputUtils.createTables(h2DataSource);
        InputUtils.populate(h2DataSource);
        airplaneDAO = new AirplaneDaoImpl(h2DataSource);
    }

    @Test
    public void shouldCorrectlySaveAirplane() {
        Airplane airplane = getAirplaneInstance("ABC123", 1L, "Grey Crows");
        int expectedSize = airplaneDAO.findAllAirplanes().size();

        airplaneDAO.saveAirplane(airplane);

        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertNotNull(airplane.getId());
        assertEquals(expectedSize + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyThrowsException() {
        Airplane airplane = getAirplaneInstance("ABC123", 200L, "Grey Crows");
        String expectedErrorMessage = String.format("%1s with id = %2d", "Can't insert into DB ", airplane.getId());

        SqlOperationException exception =
                assertThrows(SqlOperationException.class, () -> airplaneDAO.saveAirplane(airplane));

        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void shouldCorrectlyFindAirplaneByCode() {
        String code = "ABC123";
        Airplane expected = getAirplaneInstance(code, 1L, "Grey Crows");
        airplaneDAO.saveAirplane(expected);

        Airplane actual = airplaneDAO.findAirplaneByCode(code);

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCorrectlyFindAllAirplanes() {
        Airplane airplane = getAirplaneInstance("ABC123", 1L, "Grey Crows");
        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size();

        airplaneDAO.saveAirplane(airplane);

        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }

    @Test
    public void shouldCorrectlyFindAirplanesByCrewName() {
        String crewName = "Grey Crows";
        Airplane airplane = getAirplaneInstance("ABC123", 1L, crewName);
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
        Airplane airplane = getAirplaneInstance("ABC123", 1L, crewName);
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
        crew2.setName("Black Tigers");
        Airplane expected = new Airplane.Builder()
                .id(1L)
                .codeName("ABC123")
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
}
