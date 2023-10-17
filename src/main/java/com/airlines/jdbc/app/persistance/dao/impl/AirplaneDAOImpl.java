package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.DBConnector;
import static com.airlines.jdbc.app.constants.AirlinesConstants.DELETE_AIRPLANE;
import static com.airlines.jdbc.app.constants.AirlinesConstants.INSERT_AIRPLANE_SQL;
import static com.airlines.jdbc.app.constants.AirlinesConstants.SELECT_AIRPLANE_BY_NAME;
import static com.airlines.jdbc.app.constants.AirlinesConstants.SELECT_ALL_SQL;
import static com.airlines.jdbc.app.constants.AirlinesConstants.SELECT_BY_CODENAME_SQL;
import static com.airlines.jdbc.app.constants.AirlinesConstants.SELECT_CREW_BY_ID;
import static com.airlines.jdbc.app.constants.AirlinesConstants.UPDATE_AIRPLANE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_DELETE_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_ALL_EXCEPTION_MESSAGE;

import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.exception.SQLOperationException;
import com.airlines.jdbc.app.persistance.entities.AirplaneModel;
import com.airlines.jdbc.app.persistance.entities.Crew;
import static java.util.Optional.ofNullable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDAOImpl implements AirplaneDAO {
    private DataSource dataSource;

    public AirplaneDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveAirplane(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_AIRPLANE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            Long crewId = ofNullable(airplane.getCrew())
                    .map(Crew::getId).orElse(null);

            statement.setString(1, airplane.getCodeName());
            statement.setString(2, airplane.getModel().getName());
            statement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            statement.setInt(4, airplane.getCapacity());
            statement.setInt(5, airplane.getFlightRange());
            statement.setObject(6, crewId);
            statement.executeUpdate();

            Long id = fetchGeneratedId(statement);
            airplane.setId(id);
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_INSERT_EXCEPTION_MESSAGE, airplane.getId()), e);
        }
    }

    @Override
    public Airplane findAirplaneByCode(String codeName) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_CODENAME_SQL)) {

            statement.setString(1, codeName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractAirplaneFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with code name = %2s", CAN_NOT_SELECT_EXCEPTION_MESSAGE, codeName), e);
        }
        return null;
    }

    @Override
    public List<Airplane> findAllAirplanes() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

            List<Airplane> airplanes = new ArrayList<>();
            while (resultSet.next()) {
                airplanes.add(extractAirplaneFromResultSet(resultSet));
            }

            return airplanes;
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_SELECT_ALL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void deleteAirplane(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE)) {

            statement.setLong(1, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_DELETE_EXCEPTION_MESSAGE, airplane.getId()), e);
        }
    }

    @Override
    public List<Airplane> searchAirplanesByCrewName(String crewName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AIRPLANE_BY_NAME)) {

            statement.setString(1, crewName);

            List<Airplane> airplanes = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                airplanes.add(extractAirplaneFromResultSet(resultSet));
            }

            return airplanes;
        } catch (SQLException e) {
            throw new SQLOperationException(e);
        }
    }

    @Override
    public void updateAirplaneWithCrew(Airplane airplane, Crew crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AIRPLANE)) {

            statement.setLong(1, crew.getId());
            statement.setLong(2, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE, e);
        }
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new SQLOperationException("Can not obtain an account ID");
        }
    }

    private Airplane extractAirplaneFromResultSet(ResultSet resultSet) throws SQLException {
        Long crewId = resultSet.getLong("crew_id");
        Crew crew = getCrewById(crewId);

        return new Airplane.Builder()
                .id(resultSet.getLong("id"))
                .codeName(resultSet.getString("code_name"))
                .model(AirplaneModel.fromString(resultSet.getString("model")))
                .manufactureDate(LocalDate.parse(resultSet.getString("manufacture_date")))
                .capacity(resultSet.getInt("capacity"))
                .flightRange(resultSet.getInt("flight_range"))
                .crew(crew)
                .build();
    }

    private Crew getCrewById(Long crewId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CREW_BY_ID)) {

            statement.setLong(1, crewId);
            ResultSet resultSet = statement.executeQuery();

            Crew crew = null;
            while (resultSet.next()) {
                crew = extractCrewMemberFromResultSet(resultSet);
            }

            return crew;
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_SELECT_EXCEPTION_MESSAGE, crewId), e);
        }
    }

    private Crew extractCrewMemberFromResultSet(ResultSet resultSet) throws SQLException {
        Crew crew = new Crew();
        crew.setId(resultSet.getLong("id"));
        crew.setName(resultSet.getString("crew_name"));

        return crew;
    }
}
