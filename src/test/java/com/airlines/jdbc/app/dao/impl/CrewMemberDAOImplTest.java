package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.CrewMemberDAO;
import com.airlines.jdbc.app.entities.CrewMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CrewMemberDAOImplTest {
    private static final String INSERT_CREW_MEMBER =
            "insert into CrewMember (first_name, last_name, position, birthday, citizenship) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_CREW_MEMBER =
            "update CrewMember set first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? " +
                    "where id = ?";
    private static final String FIND_CREW_MEMBER_BY_ID =
            "select * from CrewMember where id = ?";

    private CrewMemberDAO crewMemberDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = Mockito.mock(Connection.class);
        crewMemberDAO = new CrewMemberDAOImpl(connection);
    }

    @Test
    public void testSaveCrewMember() throws SQLException {
        CrewMember crewMember = new CrewMember()
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA");

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(INSERT_CREW_MEMBER))).thenReturn(statement);

        crewMemberDAO.saveCrewMember(crewMember);

        verify(statement).setString(1, crewMember.getFirstName());
        verify(statement).setString(2, crewMember.getLastName());
        verify(statement).setString(3, crewMember.getPosition());
        verify(statement).setString(4, crewMember.getBirthday());
        verify(statement).setString(5, crewMember.getCitizenship());
        verify(statement).executeUpdate();
    }

    @Test
    public void testUpdateCrewMember() throws SQLException {
        CrewMember crewMember = new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA");

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(UPDATE_CREW_MEMBER))).thenReturn(statement);

        crewMemberDAO.updateCrewMember(crewMember);

        verify(statement).setString(1, crewMember.getFirstName());
        verify(statement).setString(2, crewMember.getLastName());
        verify(statement).setString(3, crewMember.getPosition());
        verify(statement).setString(4, crewMember.getBirthday());
        verify(statement).setString(5, crewMember.getCitizenship());
        verify(statement).setLong(6, crewMember.getId());
        verify(statement).executeUpdate();
    }

    @Test
    public void testFindCrewMemberById() throws SQLException {
        Long id = 1L;
        CrewMember expectedCrewMember = new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition("Pilot")
                .setBirthday("1991-01-07")
                .setCitizenship("USA");

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(FIND_CREW_MEMBER_BY_ID))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getString("position")).thenReturn("Pilot");
        when(resultSet.getString("birthday")).thenReturn("1991-01-07");
        when(resultSet.getString("citizenship")).thenReturn("USA");

        CrewMember actualCrewMember = crewMemberDAO.findCrewMemberById(id);

        assertEquals(expectedCrewMember, actualCrewMember);
    }
}