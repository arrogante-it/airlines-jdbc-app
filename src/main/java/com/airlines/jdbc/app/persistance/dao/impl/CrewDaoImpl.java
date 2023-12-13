package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.FIND_CREW_BY_ID_WITH_CREW_MEMBERS;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.INSERT_CREW_CREW_MEMBER;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.INSERT_CREW_SQL;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.SELECT_CREW_MEMBERS_BY_CREW_NAME;
import static com.airlines.jdbc.app.persistance.constants.AirlinesConstants.SELECT_CREW_MEMBERS_BY_ID;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.persistance.constants.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.CrewDao;
import com.airlines.jdbc.app.persistance.entities.Citizenship;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import com.airlines.jdbc.app.persistance.entities.Position;
import com.airlines.jdbc.app.persistance.exception.SqlOperationException;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrewDaoImpl implements CrewDao {
    private final HikariDataSource dataSource;

    public CrewDaoImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveCrew(Crew crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CREW_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, crew.getName());
            statement.executeUpdate();

            Long id = fetchGeneratedId(statement);
            crew.setId(id);
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_INSERT_EXCEPTION_MESSAGE, crew.getId()), e);
        }
    }

    @Override
    public List<CrewMember> getCrewMembersListByCrewId(Long crewId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_ID)) {

            statement.setLong(1, crewId);
            ResultSet resultSet = statement.executeQuery();

            List<CrewMember> crewMembers = new ArrayList<>();
            while (resultSet.next()) {
                crewMembers.add(extractCrewMemberFromResultSet(resultSet));
            }

            return crewMembers;
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_SELECT_EXCEPTION_MESSAGE, crewId), e);
        }
    }

    @Override
    public List<CrewMember> getCrewMembersListByCrewName(String crewName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_CREW_NAME)) {

            statement.setString(1, crewName);
            ResultSet resultSet = statement.executeQuery();

            List<CrewMember> crewMembers = new ArrayList<>();
            while (resultSet.next()) {
                crewMembers.add(extractCrewMemberFromResultSet(resultSet));
            }

            return crewMembers;
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s with crew name: %2s", CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE, crewName), e);
        }
    }

    @Override
    public void linkCrewMemberToCrew(Long crewMemberId, Long crewId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CREW_CREW_MEMBER)) {

            statement.setLong(1, crewId);
            statement.setLong(2, crewMemberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlOperationException(CAN_NOT_INSERT_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Crew findCrewById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CREW_BY_ID_WITH_CREW_MEMBERS)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Crew result = null;
            List<CrewMember> crewMembers = new ArrayList<>();

            while (resultSet.next()) {
                if (result == null) {
                    result = new Crew.Builder()
                            .id(resultSet.getLong("crew_id"))
                            .name(resultSet.getString("crew_name"))
                            .build();

                }

                crewMembers.add(extractCrewMemberFromResultSet(resultSet));
            }

            if (result != null) {
                result.setCrewMembers(crewMembers);
            }

            return result;
        } catch (SQLException e) {
            throw new SqlOperationException(
                    String.format("%1s %2d", CAN_NOT_SELECT_EXCEPTION_MESSAGE, id), e);
        }
    }

    private Long fetchGeneratedId(PreparedStatement insertStatement) throws SQLException {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new SqlOperationException("Can not obtain an crew ID");
        }
    }

    private CrewMember extractCrewMemberFromResultSet(ResultSet resultSet) throws SQLException {
        return new CrewMember.Builder()
                .id(resultSet.getLong("member_id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .position(Position.fromString(resultSet.getString("position")))
                .birthday(LocalDate.parse(resultSet.getString("birthday")))
                .citizenship(Citizenship.fromString(resultSet.getString("citizenship")))
                .build();
    }
}
