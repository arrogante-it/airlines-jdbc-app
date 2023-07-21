package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.AirPlaneDAO;
import com.airlines.jdbc.app.entities.Airplane;
import com.airlines.jdbc.app.entities.Crew;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AirPlaneDAOImpl implements AirPlaneDAO {

    private static final String INSERT_AIRPLANE_SQL =
            "insert into Airplane (code_name, model, manufacture_date, capacity, flight_range) values (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_CODENAME_SQL =
            "select * from Airplane where codeName = ?";
    private static final String SELECT_ALL_SQL =
            "select * from Airplane";
    private static final String DELETE_AIRPLANE =
            "delete from Airplane where id = ?";
    private static final String SELECT_AIRPLANE_BY_NAME =
            "select * from Airplane where crew_id in (select id from Crew where name = ?)";
    private static final String UPDATE_AIRPLANE =
            "update Airplane set crew_id = ? where id = ?";


    private final Connection connection;

    public AirPlaneDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAirplane(Airplane airplane) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AIRPLANE_SQL)) {
            statement.setString(1, airplane.getCodeName());
            statement.setString(2, airplane.getModel());
            statement.setString(3, airplane.getManufactureDate());
            statement.setInt(4, airplane.getCapacity());
            statement.setInt(5, airplane.getFlightRange());
            statement.executeUpdate();
        }
    }

    @Override
    public Airplane findAirplaneByCode(String codeName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_CODENAME_SQL)) {
            statement.setString(1, codeName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAirplaneFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Airplane> findAllAirplanes() throws SQLException {
        List<Airplane> airplanes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                airplanes.add(extractAirplaneFromResultSet(resultSet));
            }
        }
        return airplanes;
    }

    @Override
    public void deleteAirplane(Airplane airplane) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE)) {
            statement.setLong(1, airplane.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Airplane> searchAirplanesByCrewName(String crewName) throws SQLException {
        List<Airplane> airplanes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AIRPLANE_BY_NAME)) {
            statement.setString(1, crewName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    airplanes.add(extractAirplaneFromResultSet(resultSet));
                }
            }
        }
        return airplanes;
    }

    @Override
    public void updateAirplaneWithCrew(Airplane airplane, Crew crew) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AIRPLANE)) {
            statement.setLong(1, crew.getId());
            statement.setLong(2, airplane.getId());
            statement.executeUpdate();
        }
    }

    private Airplane extractAirplaneFromResultSet(ResultSet resultSet) throws SQLException {
        Airplane airplane = new Airplane();
        airplane.setId(resultSet.getLong("id"));
        airplane.setCodeName(resultSet.getString("code_name"));
        airplane.setModel(resultSet.getString("model"));
        airplane.setManufactureDate(resultSet.getString("manufacture_date"));
        airplane.setCapacity(resultSet.getInt("capacity"));
        airplane.setFlightRange(resultSet.getInt("flight_range"));
        return airplane;
    }
}
