package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.CrewDAO;
import com.airlines.jdbc.app.entities.Crew;
import com.airlines.jdbc.app.entities.CrewMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDAOImpl implements CrewDAO {
    private static final String INSERT_CREW_CREW_MEMBER =
            "insert into Crew_crew_member (crew_id, crew_member_id) values (?, ?)";
    private static final String SELECT_CREW_MEMBERS_BY_ID =
            "select cm.* from CrewMember cm join Crew_crew_Member cc on cm.id = cc.crew_member_id where cc.crew_id = ?";
    private static final String SELECT_CREW_MEMBERS_BY_CREW_NAME =
            "select cm.* from CrewMember cm join Crew_CrewMember cc on cm.id = cc.crew_member_id " +
                    "join Crew c on cc.crew_id = c.id where c.name = ?";
    private static final String INSERT_CREW_CREW_MEMBER_TO_CREW =
            "insert into Crew_Crew_Member (crew_id, crew_member_id) values (?, ?)";

    private final Connection connection;

    public CrewDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addNewCrewMemberToCrew(Crew crew, CrewMember crewMember) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_CREW_MEMBER)) {
            statement.setLong(1, crew.getId());
            statement.setLong(2, crewMember.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<CrewMember> getListOfCrewMembersByCrewId(Long crewId) throws SQLException {
        List<CrewMember> crewMembers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_ID)) {
            statement.setLong(1, crewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    crewMembers.add(extractCrewMemberFromResultSet(resultSet));
                }
            }
        }
        return crewMembers;
    }

    @Override
    public List<CrewMember> getListOfCrewMemberByCrewName(String crewName) throws SQLException {
        List<CrewMember> crewMembers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CREW_MEMBERS_BY_CREW_NAME)) {
            statement.setString(1, crewName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    crewMembers.add(extractCrewMemberFromResultSet(resultSet));
                }
            }
        }
        return crewMembers;
    }

    @Override
    public void linkCrewMemberToCrew(Long crewMemberId, Long crewId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CREW_CREW_MEMBER_TO_CREW)) {
            statement.setLong(1, crewId);
            statement.setLong(2, crewMemberId);
            statement.executeUpdate();
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
