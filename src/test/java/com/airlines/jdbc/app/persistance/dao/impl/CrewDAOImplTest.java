package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.persistance.dao.CrewDAO;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CrewDAOImplTest {
    private static final String INSERT_CREW_CREW_MEMBER =
            "insert into crew_crew_member (crew_id, crew_member_id) values (?, ?)";
    private static final String SELECT_CREW_MEMBERS_BY_ID =
            "select cm.* from crewmember cm join crew_crew_member cc on cm.id = cc.crew_member_id where cc.crew_id = ?";
    private static final String SELECT_CREW_MEMBERS_BY_CREW_NAME =
            "select cm.* from crewmember cm join crew_crewmember cc on cm.id = cc.crew_member_id " +
                    "join crew c on cc.crew_id = c.id where c.name = ?";
    private static final String INSERT_CREW_CREW_MEMBER_TO_CREW =
            "insert into crew_crew_member (crew_id, crew_member_id) values (?, ?)";

    private CrewDAO crewDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = Mockito.mock(Connection.class);
        crewDAO = new CrewDAOImpl(connection);
    }

    @Test
    public void testAddNewCrewMemberToCrew() throws SQLException {
        Crew crew = new Crew()
                .setId(1L)
                .setName("Crew A");
        CrewMember crewMember = new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA");

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        when(connection.prepareStatement(Mockito.eq(INSERT_CREW_CREW_MEMBER))).thenReturn(statement);

        crewDAO.addNewCrewMemberToCrew(crew, crewMember);

        Mockito.verify(statement).setLong(1, crew.getId());
        Mockito.verify(statement).setLong(2, crewMember.getId());
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    public void testGetListOfCrewMembersByCrewId() throws SQLException {
        Long crewId = 1L;
        List<CrewMember> expectedCrewMembers = new ArrayList<>();

        expectedCrewMembers.add(new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA"));
        expectedCrewMembers.add(new CrewMember()
                .setId(2L)
                .setFirstName("John")
                .setLastName("London")
                .setPosition("Pilot")
                .setBirthday("1986-04-26")
                .setCitizenship("UK"));

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(SELECT_CREW_MEMBERS_BY_ID))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("first_name")).thenReturn("John", "John");
        when(resultSet.getString("last_name")).thenReturn("Doe", "London");
        when(resultSet.getString("position")).thenReturn("Pilot", "Pilot");
        when(resultSet.getString("birthday")).thenReturn("1991-01-07", "1986-04-26");
        when(resultSet.getString("citizenship")).thenReturn("USA", "UK");

        List<CrewMember> actualCrewMembers = crewDAO.getListOfCrewMembersByCrewId(crewId);

        assertEquals(expectedCrewMembers, actualCrewMembers);
    }

    @Test
    public void testGetListOfCrewMemberByCrewName() throws SQLException {
        String crewName = "Crew A";
        List<CrewMember> expectedCrewMembers = new ArrayList<>();

        expectedCrewMembers.add(new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA"));
        expectedCrewMembers.add(new CrewMember()
                .setId(2L)
                .setFirstName("John")
                .setLastName("London")
                .setPosition("Pilot")
                .setBirthday("1986-04-26")
                .setCitizenship("UK"));

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(SELECT_CREW_MEMBERS_BY_CREW_NAME))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("first_name")).thenReturn("John", "John");
        when(resultSet.getString("last_name")).thenReturn("Doe", "London");
        when(resultSet.getString("position")).thenReturn("Pilot", "Pilot");
        when(resultSet.getString("birthday")).thenReturn("1991-01-07", "1986-04-26");
        when(resultSet.getString("citizenship")).thenReturn("USA", "UK");

        List<CrewMember> actualCrewMembers = crewDAO.getListOfCrewMemberByCrewName(crewName);

        assertEquals(expectedCrewMembers, actualCrewMembers);
    }

    @Test
    public void testLinkCrewMemberToCrew() throws SQLException {
        long crewMemberId = 1L;
        long crewId = 2L;

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(INSERT_CREW_CREW_MEMBER_TO_CREW))).thenReturn(statement);

        crewDAO.linkCrewMemberToCrew(crewMemberId, crewId);

        Mockito.verify(statement).setLong(1, crewId);
        Mockito.verify(statement).setLong(2, crewMemberId);
        Mockito.verify(statement).executeUpdate();
    }
}