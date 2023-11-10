package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.InputUtil;
import com.airlines.jdbc.app.TestDataSourceProvider;
import com.airlines.jdbc.app.persistance.dao.CrewDao;
import static com.airlines.jdbc.app.persistance.entities.Citizenship.UK;
import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;
import static com.airlines.jdbc.app.persistance.entities.Position.COR;
import com.airlines.jdbc.app.persistance.exception.SqlOperationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

class CrewDaoImplTest {
    private CrewDao crewDao;

    @BeforeEach
    public void setUp() {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        InputUtil.createTables(h2DataSource);
        InputUtil.populate(h2DataSource);
        crewDao = new CrewDaoImpl(h2DataSource);
    }

    @Test
    public void shouldCorrectlySaveCrew() {
        Crew expected = new Crew.Builder()
                .name("Dumbledore's Army")
                .build();

        crewDao.saveCrew(expected);

        Crew actual = crewDao.findCrewById(6L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldCorrectlyThrowsException() {
        Crew crew = new Crew.Builder().build();
        String expectedErrorMessage = String.format("%1s with id = %2d", "Can't insert into DB ", crew.getId());

        SqlOperationException exception =
                assertThrows(SqlOperationException.class, () -> crewDao.saveCrew(crew));

        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void shouldCorrectlyGetListOfCrewMembersByCrewId() {
        Long crewMemberId = 2L;
        CrewMember expected = getCrewMemberInstance(crewMemberId);
        crewDao.linkCrewMemberToCrew(crewMemberId, 1L);

        List<CrewMember> crewMembers = crewDao.getCrewMembersListByCrewId(1L);

        assertNotNull(crewMembers);
        assertEquals(expected, crewMembers.get(0));
        assertEquals(expected.getId(), crewMembers.get(0).getId());
    }

    @Test
    public void shouldCorrectlyGetListOfCrewMemberByCrewName() {
        Long crewMemberId = 2L;
        CrewMember expected = getCrewMemberInstance(crewMemberId);
        crewDao.linkCrewMemberToCrew(crewMemberId, 1L);

        List<CrewMember> crewMembers = crewDao.getCrewMembersListByCrewName("Grey Crows");

        assertNotNull(crewMembers);
        assertEquals(expected, crewMembers.get(0));
        assertEquals(expected.getId(), crewMembers.get(0).getId());
    }

    @Test
    public void shouldCorrectlyLinkCrewMemberToCrew() {
        Long crewMemberId = 2L;
        CrewMember expected = getCrewMemberInstance(crewMemberId);

        crewDao.linkCrewMemberToCrew(crewMemberId, 1L);

        List<CrewMember> crewMembers = crewDao.getCrewMembersListByCrewId(1L);

        assertNotNull(crewMembers.get(0));
        assertEquals(expected, crewMembers.get(0));
        assertEquals(expected.getId(), crewMembers.get(0).getId());
    }

    @Test
    public void shouldCorrectlyFindCrewById() {
        Crew expected = new Crew.Builder()
                .id(1L)
                .name("Grey Crows")
                .build();

        Crew actual = crewDao.findCrewById(1L);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
    }

    private CrewMember getCrewMemberInstance(Long crewMemberId) {
        return new CrewMember.Builder()
                .id(crewMemberId)
                .firstName("Jesse")
                .lastName("Pinkman")
                .position(COR)
                .birthday(LocalDate.parse("1995-06-11"))
                .citizenship(UK)
                .build();
    }
}