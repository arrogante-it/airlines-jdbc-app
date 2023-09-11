package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.constants.AirplaneConstants.INSERT_AIRPLANE_SQL;
import static com.airlines.jdbc.app.constants.AirplaneConstants.DELETE_AIRPLANE;
import static com.airlines.jdbc.app.constants.AirplaneConstants.SELECT_AIRPLANE_BY_NAME;
import static com.airlines.jdbc.app.constants.AirplaneConstants.SELECT_ALL_SQL;
import static com.airlines.jdbc.app.constants.AirplaneConstants.SELECT_BY_CODENAME_SQL;
import static com.airlines.jdbc.app.constants.AirplaneConstants.UPDATE_AIRPLANE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_DELETE_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_ALL_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.exception.SQLOperationException;
import com.airlines.jdbc.app.persistance.entities.enamFields.AirplaneModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDAOImpl implements AirplaneDAO {
    private final Connection connection;

    public AirplaneDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAirplane(Airplane airplane) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AIRPLANE_SQL)) {
            statement.setString(1, airplane.getCodeName());
            statement.setObject(2, airplane.getModel());
            statement.setString(3, airplane.getManufactureDate());
            statement.setInt(4, airplane.getCapacity());
            statement.setInt(5, airplane.getFlightRange());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_INSERT_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Airplane findAirplaneByCode(String codeName) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_CODENAME_SQL)) {
            statement.setString(1, codeName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractAirplaneFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_SELECT_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public List<Airplane> findAllAirplanes() {
        List<Airplane> airplanes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                airplanes.add(extractAirplaneFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_SELECT_ALL_EXCEPTION_MESSAGE, e);
        }
        return airplanes;
    }

    @Override
    public void deleteAirplane(Airplane airplane) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE)) {
            statement.setLong(1, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_DELETE_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Airplane> searchAirplanesByCrewName(String crewName) {
        List<Airplane> airplanes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_AIRPLANE_BY_NAME)) {
            statement.setString(1, crewName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                airplanes.add(extractAirplaneFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLOperationException(e);
        }
        return airplanes;
    }

    @Override
    public void updateAirplaneWithCrew(Airplane airplane, Crew crew) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AIRPLANE)) {
            statement.setLong(1, crew.getId());
            statement.setLong(2, airplane.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE, e);
        }
    }

    private Airplane extractAirplaneFromResultSet(ResultSet resultSet) throws SQLException {
        Airplane airplane = new Airplane();

        airplane.setId(resultSet.getLong("id"));
        airplane.setCodeName(resultSet.getString("code_name"));
        airplane.setModel((AirplaneModel) resultSet.getObject("model"));
        airplane.setManufactureDate(resultSet.getString("manufacture_date"));
        airplane.setCapacity(resultSet.getInt("capacity"));
        airplane.setFlightRange(resultSet.getInt("flight_range"));

        return airplane;
    }
}
