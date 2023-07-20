package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.AirPlaneDAO;
import com.airlines.jdbc.app.entities.Airplane;
import com.airlines.jdbc.app.entities.Crew;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AirPlaneDAOImpl implements AirPlaneDAO {

    private static final String INSERT_AIRPLANE_SQL =
            "insert into Airplane (code_name, model, manufacture_date, capacity, flight_range) values (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_BY_CODENAME_SQL =
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

    }

    @Override
    public Airplane findAirplaneByCode(String codeName) throws SQLException {
        return null;
    }

    @Override
    public List<Airplane> findAllAirplanes() throws SQLException {
        return null;
    }

    @Override
    public void deleteAirplane(Airplane airplane) throws SQLException {

    }

    @Override
    public List<Airplane> searchAirplanesByCrewName(String crewName) throws SQLException {
        return null;
    }

    @Override
    public void updateAirplaneWithCrew(Airplane airplane, Crew crew) throws SQLException {

    }
}
