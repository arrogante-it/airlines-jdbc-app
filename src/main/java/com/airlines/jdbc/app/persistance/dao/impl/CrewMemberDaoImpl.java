package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.constants.AirlinesConstants.FIND_CREW_MEMBER_BY_ID;
import static com.airlines.jdbc.app.constants.AirlinesConstants.INSERT_CREW_MEMBER;
import static com.airlines.jdbc.app.constants.AirlinesConstants.UPDATE_CREW_MEMBER;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_UPDATE_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.CrewMemberDao;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import com.airlines.jdbc.app.exception.SQLOperationException;
import com.airlines.jdbc.app.persistance.entities.CrewMemberCitizenship;
import com.airlines.jdbc.app.persistance.entities.CrewMemberPosition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CrewMemberDaoImpl implements CrewMemberDao {
    private final DataSource dataSource;

    public CrewMemberDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveCrewMember(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CREW_MEMBER)) {

            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setObject(3, crewMember.getPosition());
            statement.setObject(4, crewMember.getBirthday());
            statement.setObject(5, crewMember.getCitizenship());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(CAN_NOT_INSERT_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void updateCrewMember(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CREW_MEMBER)) {

            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setObject(3, crewMember.getPosition());
            statement.setObject(4, crewMember.getBirthday());
            statement.setObject(5, crewMember.getCitizenship());
            statement.setLong(6, crewMember.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_UPDATE_EXCEPTION_MESSAGE, crewMember.getId()), e);
        }
    }

    @Override
    public CrewMember findCrewMemberById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CREW_MEMBER_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCrewMemberFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SQLOperationException(
                    String.format("%1s with id = %2d", CAN_NOT_SELECT_EXCEPTION_MESSAGE, id), e);
        }
        return null;
    }

    private CrewMember extractCrewMemberFromResultSet(ResultSet resultSet) throws SQLException {
        CrewMember crewMember = new CrewMember.Builder().build();
        crewMember.setId(resultSet.getLong("id"));
        crewMember.setFirstName(resultSet.getString("first_name"));
        crewMember.setLastName(resultSet.getString("last_name"));
        crewMember.setPosition((CrewMemberPosition) resultSet.getObject("position"));
        crewMember.setBirthday((LocalDate) resultSet.getObject("birthday"));
        crewMember.setCitizenship((CrewMemberCitizenship) resultSet.getObject("citizenship"));

        return crewMember;
    }
}
