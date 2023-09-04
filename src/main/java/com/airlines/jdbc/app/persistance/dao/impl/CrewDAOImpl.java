package com.airlines.jdbc.app.persistance.dao.impl;

import static com.airlines.jdbc.app.constants.CrewConstants.INSERT_CREW_CREW_MEMBER;
import static com.airlines.jdbc.app.constants.CrewConstants.INSERT_CREW_CREW_MEMBER_TO_CREW;
import static com.airlines.jdbc.app.constants.CrewConstants.SELECT_CREW_MEMBERS_BY_CREW_NAME;
import static com.airlines.jdbc.app.constants.CrewConstants.SELECT_CREW_MEMBERS_BY_ID;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_INSERT_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE;
import static com.airlines.jdbc.app.exception.ExceptionConstants.CAN_NOT_SELECT_EXCEPTION_MESSAGE;
import com.airlines.jdbc.app.persistance.dao.CrewDAO;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import com.airlines.jdbc.app.exception.SQLCustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDAOImpl implements CrewDAO {
    private final Connection connection;

    public CrewDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addNewCrewMemberToCrew(Crew crew, CrewMember crewMember) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_CREW_MEMBER)) {
            statement.setLong(1, crew.getId());
            statement.setLong(2, crewMember.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLCustomException(CAN_NOT_INSERT_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<CrewMember> getListOfCrewMembersByCrewId(Long crewId) {
        List<CrewMember> crewMembers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_ID)) {
            statement.setLong(1, crewId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                crewMembers.add(extractCrewMemberFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLCustomException(CAN_NOT_SELECT_EXCEPTION_MESSAGE, e);
        }
        return crewMembers;
    }

    @Override
    public List<CrewMember> getListOfCrewMemberByCrewName(String crewName) {
        List<CrewMember> crewMembers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_CREW_NAME)) {
            statement.setString(1, crewName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                crewMembers.add(extractCrewMemberFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLCustomException(CAN_NOT_SELECT_BY_NAME_EXCEPTION_MESSAGE, e);
        }
        return crewMembers;
    }

    @Override
    public void linkCrewMemberToCrew(Long crewMemberId, Long crewId) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_CREW_MEMBER_TO_CREW)) {
            statement.setLong(1, crewId);
            statement.setLong(2, crewMemberId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLCustomException(CAN_NOT_INSERT_EXCEPTION_MESSAGE, e);
        }
    }

    private CrewMember extractCrewMemberFromResultSet(ResultSet resultSet) throws SQLException {
        CrewMember crewMember = new CrewMember();

        crewMember.setId(resultSet.getLong("id"));
        crewMember.setFirstName(resultSet.getString("first_name"));
        crewMember.setLastName(resultSet.getString("last_name"));
        crewMember.setPosition(resultSet.getString("position"));
        crewMember.setBirthday(resultSet.getString("birthday"));
        crewMember.setCitizenship(resultSet.getString("citizenship"));

        return crewMember;
    }
}
