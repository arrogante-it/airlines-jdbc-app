package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.FIND_CREW_MEMBER_BY_ID;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.INSERT_CREW_MEMBER;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.UPDATE_CREW_MEMBER;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_UPDATE_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.CrewMemberDao;
import com.airlines.jdbc.app.persistance.entities.Citizenship;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import com.airlines.jdbc.app.persistance.entities.Position;
import com.airlines.jdbc.app.persistance.exception.SqlOperationException;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CrewMemberDaoImpl implements CrewMemberDao {
    private final HikariDataSource dataSource;

    public CrewMemberDaoImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveCrewMember(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CREW_MEMBER, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setObject(3, crewMember.getPosition().getName());
            statement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            statement.setObject(5, crewMember.getCitizenship().getName());
            statement.executeUpdate();

            Long id = fetchGeneratedId(statement);
            crewMember.setId(id);
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_INSERT_EXCEPTION_MESSAGE, crewMember.getId()), e);
        }
    }

    @Override
    public void updateCrewMember(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CREW_MEMBER)) {

            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setString(3, crewMember.getPosition().getName());
            statement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            statement.setString(5, crewMember.getCitizenship().getName());
            statement.setLong(6, crewMember.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_UPDATE_EXCEPTION_MESSAGE, crewMember.getId()), e);
        }
    }

    @Override
    public CrewMember findCrewMemberById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CREW_MEMBER_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            CrewMember result = null;
            if (resultSet.next()) {
                result = extractCrewMemberFromResultSet(resultSet);
            }

            return result;
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_SELECT_EXCEPTION_MESSAGE, id), e);
        }
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new SqlOperationException("Can not obtain an crewMember ID");
        }
    }

    private CrewMember extractCrewMemberFromResultSet(ResultSet resultSet) throws SQLException {
        return new CrewMember.Builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .position(Position.fromString(resultSet.getString("position")))
                .birthday(LocalDate.parse(resultSet.getString("birthday")))
                .citizenship(Citizenship.fromString(resultSet.getString("citizenship")))
                .build();
    }
}
