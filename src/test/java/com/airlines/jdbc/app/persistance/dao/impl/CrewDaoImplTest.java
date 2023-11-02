package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.FileReader;
import com.airlines.jdbc.app.TestDataSourceProvider;
import com.airlines.jdbc.app.persistance.dao.CrewDao;
import com.airlines.jdbc.app.persistance.entities.Crew;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class CrewDaoImplTest {
    private CrewDao crewDao;

    @BeforeEach
    public void setUp() throws SQLException {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        createTables(h2DataSource);
        populate(h2DataSource);
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
    public void shouldCorrectlyGetListOfCrewMembersByCrewId() {
    }

    @Test
    public void shouldCorrectlyGetListOfCrewMemberByCrewName() {
    }

    @Test
    public void shouldCorrectlyLinkCrewMemberToCrew() {
    }

    @Test
    public void shouldCorrectlyFindCrewById() {
    }

    private void createTables(DataSource dataSource) throws SQLException {
        String createTablesSql = new FileReader().readWholeFileFromResources("create_tables.sql");

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            statement.close();
        }
    }

    private void populate(DataSource dataSource) throws SQLException {
        String populateSql = new FileReader().readWholeFileFromResources("populate.sql");

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(populateSql);
            statement.close();
        }
    }
}