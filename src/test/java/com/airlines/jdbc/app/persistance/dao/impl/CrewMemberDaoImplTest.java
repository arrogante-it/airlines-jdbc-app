package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.InputUtil;
import com.airlines.jdbc.app.TestDataSourceProvider;
import com.airlines.jdbc.app.persistance.dao.CrewMemberDao;
import static com.airlines.jdbc.app.persistance.entities.Citizenship.UK;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import static com.airlines.jdbc.app.persistance.entities.Position.COR;
import com.airlines.jdbc.app.persistance.exception.SqlOperationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDate;

class CrewMemberDaoImplTest {
    private CrewMemberDao crewMemberDao;

    @BeforeEach
    public void setUp() {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        InputUtil.createTables(h2DataSource);
        InputUtil.populate(h2DataSource);
        crewMemberDao = new CrewMemberDaoImpl(h2DataSource);
    }

    @Test
    public void shouldCorrectlySaveCrewMember() {
        CrewMember expected = new CrewMember.Builder()
                .firstName("Robert")
                .lastName("Paulson")
                .position(COR)
                .birthday(LocalDate.parse("1995-06-11"))
                .citizenship(UK)
                .build();
        crewMemberDao.saveCrewMember(expected);

        CrewMember actual = crewMemberDao.findCrewMemberById(6L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldCorrectlyThrowsException() {
        String moreThanFourtySymbols = "Robertwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
        CrewMember crewMember = new CrewMember.Builder()
                .firstName(moreThanFourtySymbols)
                .lastName("Paulson")
                .position(COR)
                .birthday(LocalDate.parse("1995-06-11"))
                .citizenship(UK)
                .build();
        String expectedErrorMessage = String.format("%1s with id = %2d", "Can't insert into DB ", crewMember.getId());

        SqlOperationException exception =
                assertThrows(SqlOperationException.class, () -> crewMemberDao.saveCrewMember(crewMember));

        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void shouldCorrectlyUpdateCrewMember() {
        Long commonId = 1L;
        CrewMember expected = new CrewMember.Builder()
                .id(commonId)
                .firstName("Robert")
                .lastName("Paulson")
                .position(COR)
                .birthday(LocalDate.parse("1995-06-11"))
                .citizenship(UK)
                .build();
        CrewMember actual1 = crewMemberDao.findCrewMemberById(commonId);

        crewMemberDao.updateCrewMember(expected);

        CrewMember actual2 = crewMemberDao.findCrewMemberById(commonId);

        assertNotEquals(expected, actual1);
        assertEquals(expected, actual2);
        assertEquals(expected.getId(), actual1.getId());
        assertEquals(expected.getId(), actual2.getId());
    }

    @Test
    public void shouldCorrectlyFindCrewMemberById() {
        Long commonId = 2L;
        CrewMember expected = new CrewMember.Builder()
                .id(commonId)
                .firstName("Jesse")
                .lastName("Pinkman")
                .position(COR)
                .birthday(LocalDate.parse("1995-06-11"))
                .citizenship(UK)
                .build();

        CrewMember actual = crewMemberDao.findCrewMemberById(commonId);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
    }
}