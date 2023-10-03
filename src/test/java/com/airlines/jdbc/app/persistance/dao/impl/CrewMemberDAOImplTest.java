package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.persistance.dao.CrewMemberDAO;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import static com.airlines.jdbc.app.persistance.entities.CrewMemberCitizenship.UK;
import static com.airlines.jdbc.app.persistance.entities.CrewMemberPosition.CAP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CrewMemberDAOImplTest {
    private static final String INSERT_CREW_MEMBER =
            "insert into crewmember (first_name, last_name, position, birthday, citizenship) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_CREW_MEMBER =
            "update crewmember set first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? " +
                    "where id = ?";
    private static final String FIND_CREW_MEMBER_BY_ID =
            "select * from crewmember where id = ?";

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
                .setPosition(CAP)
                .setBirthday(LocalDate.parse("1991-01-07"))
                .setCitizenship(UK);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(INSERT_CREW_MEMBER))).thenReturn(statement);

        crewMemberDAO.saveCrewMember(crewMember);

        verify(statement).setString(1, crewMember.getFirstName());
        verify(statement).setString(2, crewMember.getLastName());
        verify(statement).setObject(3, crewMember.getPosition());
        verify(statement).setObject(4, crewMember.getBirthday());
        verify(statement).setObject(5, crewMember.getCitizenship());
        verify(statement).executeUpdate();
    }

    @Test
    public void testUpdateCrewMember() throws SQLException {
        CrewMember crewMember = new CrewMember()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPosition(CAP)
                .setBirthday(LocalDate.parse("1991-01-07"))
                .setCitizenship(UK);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.eq(UPDATE_CREW_MEMBER))).thenReturn(statement);

        crewMemberDAO.updateCrewMember(crewMember);

        verify(statement).setString(1, crewMember.getFirstName());
        verify(statement).setString(2, crewMember.getLastName());
        verify(statement).setObject(3, crewMember.getPosition());
        verify(statement).setObject(4, crewMember.getBirthday());
        verify(statement).setObject(5, crewMember.getCitizenship());
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
                .setPosition(CAP)
                .setBirthday(LocalDate.parse("1991-01-07"))
                .setCitizenship(UK);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.eq(FIND_CREW_MEMBER_BY_ID))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getObject("position")).thenReturn(CAP);
        when(resultSet.getObject("birthday")).thenReturn(LocalDate.parse("1991-01-07"));
        when(resultSet.getObject("citizenship")).thenReturn(UK);

        CrewMember actualCrewMember = crewMemberDAO.findCrewMemberById(id);

        assertEquals(expectedCrewMember, actualCrewMember);
    }
}