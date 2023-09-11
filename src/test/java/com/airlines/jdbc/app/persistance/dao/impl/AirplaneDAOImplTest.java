package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.enamFields.AirplaneModel;
import static com.airlines.jdbc.app.persistance.entities.enamFields.AirplaneModel.AIRBUS;
import static com.airlines.jdbc.app.persistance.entities.enamFields.AirplaneModel.BOEING;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AirplaneDAOImplTest {
    private static final String INSERT_AIRPLANE_SQL =
            "insert into airplane (code_name, model, manufacture_date, capacity, flight_range) values (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_CODENAME_SQL =
            "select * from airplane where codeName = ?";
    private static final String SELECT_ALL_SQL =
            "select * from airplane";
    private static final String DELETE_AIRPLANE =
            "delete from airplane where id = ?";
    private static final String SELECT_AIRPLANE_BY_NAME =
            "select * from airplane where crew_id in (select id from crew where name = ?)";
    private static final String UPDATE_AIRPLANE =
            "update airplane set crew_id = ? where id = ?";

    private AirplaneDAO airPlaneDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = Mockito.mock(Connection.class);
        airPlaneDAO = new AirplaneDAOImpl(connection);
    }

    @Test
    public void testSaveAirplane() throws SQLException {
        Airplane airplane = new Airplane()
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(INSERT_AIRPLANE_SQL))).thenReturn(statement);

        airPlaneDAO.saveAirplane(airplane);

        verify(statement).setString(1, airplane.getCodeName());
        verify(statement).setObject(2, airplane.getModel());
        verify(statement).setString(3, airplane.getManufactureDate());
        verify(statement).setInt(4, airplane.getCapacity());
        verify(statement).setInt(5, airplane.getFlightRange());
        verify(statement).executeUpdate();
    }

    @Test
    public void testFindAirplaneByCode() throws SQLException {
        String codeName = "ABC123";

        Airplane expectedAirplane = new Airplane()
                .setId(1L)
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(SELECT_BY_CODENAME_SQL))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("code_name")).thenReturn("ABC123");
        when(resultSet.getString("model")).thenReturn(BOEING.getName());
        when(resultSet.getString("manufacture_date")).thenReturn("2000-03-03");
        when(resultSet.getInt("capacity")).thenReturn(300);
        when(resultSet.getInt("flight_range")).thenReturn(7000);

        Airplane actualAirplane = airPlaneDAO.findAirplaneByCode(codeName);

        assertEquals(expectedAirplane, actualAirplane);
    }

    @Test
    public void testFindAllAirplanes() throws SQLException {
        List<Airplane> expectedAirplanes = new ArrayList<>();

        expectedAirplanes.add(new Airplane()
                .setId(1L)
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000));
        expectedAirplanes.add(new Airplane()
                .setId(2L)
                .setCodeName("ZXC123")
                .setModel(AIRBUS)
                .setManufactureDate("2007-07-07")
                .setCapacity(200)
                .setFlightRange(5000));

        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(SELECT_ALL_SQL)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("code_name")).thenReturn("ABC123", "ZXC123");
        when(resultSet.getString("model")).thenReturn(BOEING.getName(), AIRBUS.getName());
        when(resultSet.getString("manufacture_date")).thenReturn("2000-03-03", "2007-07-07");
        when(resultSet.getInt("capacity")).thenReturn(300, 200);
        when(resultSet.getInt("flight_range")).thenReturn(7000, 5000);

        List<Airplane> actualAirplanes = airPlaneDAO.findAllAirplanes();

        assertEquals(expectedAirplanes, actualAirplanes);
    }

    @Test
    public void testDeleteAirplane() throws SQLException {
        Airplane airplane = new Airplane()
                .setId(1L)
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(DELETE_AIRPLANE))).thenReturn(statement);

        airPlaneDAO.deleteAirplane(airplane);

        verify(statement).setLong(1, airplane.getId());
        verify(statement).executeUpdate();
    }

    @Test
    public void testSearchAirplanesByCrewName() throws SQLException {
        String crewName = "Crew A";

        List<Airplane> expectedAirplanes = new ArrayList<>();

        expectedAirplanes.add(new Airplane()
                .setId(1L)
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000));
        expectedAirplanes.add(new Airplane()
                .setId(2L)
                .setCodeName("ZXC123")
                .setModel(AIRBUS)
                .setManufactureDate("2007-07-07")
                .setCapacity(200)
                .setFlightRange(5000));

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(SELECT_AIRPLANE_BY_NAME))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("code_name")).thenReturn("ABC123", "ZXC123");
        when(resultSet.getString("model")).thenReturn(BOEING.getName(), AIRBUS.getName());
        when(resultSet.getString("manufacture_date")).thenReturn("2000-03-03", "2007-07-07");
        when(resultSet.getInt("capacity")).thenReturn(300, 200);
        when(resultSet.getInt("flight_range")).thenReturn(7000, 5000);

        List<Airplane> actualAirplanes = airPlaneDAO.searchAirplanesByCrewName(crewName);

        assertEquals(expectedAirplanes, actualAirplanes);
    }

    @Test
    public void testUpdateAirplaneWithCrew() throws SQLException {
        Airplane airplane = new Airplane()
                .setId(1L)
                .setCodeName("ABC123")
                .setModel(BOEING)
                .setManufactureDate("2000-03-03")
                .setCapacity(300)
                .setFlightRange(7000);
        Crew crew = new Crew()
                .setId(1L)
                .setName("Crew A");

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(UPDATE_AIRPLANE))).thenReturn(statement);

        airPlaneDAO.updateAirplaneWithCrew(airplane, crew);

        verify(statement).setLong(1, crew.getId());
        verify(statement).setLong(2, airplane.getId());
        verify(statement).executeUpdate();
    }
}