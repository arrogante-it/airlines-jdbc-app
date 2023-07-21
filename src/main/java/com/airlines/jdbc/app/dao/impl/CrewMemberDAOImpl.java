package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.CrewMemberDAO;
import com.airlines.jdbc.app.entities.CrewMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrewMemberDAOImpl implements CrewMemberDAO {
    private static final String INSERT_CREW_MEMBER =
            "insert into CrewMember (first_name, last_name, position, birthday, citizenship) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_CREW_MEMBER =
            "update CrewMember set first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? " +
                    "where id = ?";
    private static final String FIND_CREW_MEMBER_BY_ID =
            "select * from CrewMember where id = ?";

    private final Connection connection;

    public CrewMemberDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveCrewMember(CrewMember crewMember) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_MEMBER)) {
            saveCrewMember(crewMember, connection);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateCrewMember(CrewMember crewMember) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CREW_MEMBER)) {
            saveCrewMember(crewMember, connection);
            statement.setLong(6, crewMember.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public CrewMember findCrewMemberById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_CREW_MEMBER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCrewMemberFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    private void saveCrewMember(CrewMember crewMember, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_MEMBER)) {
            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setString(3, crewMember.getPosition());
            statement.setString(4, crewMember.getBirthday());
            statement.setString(5, crewMember.getCitizenship());
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
