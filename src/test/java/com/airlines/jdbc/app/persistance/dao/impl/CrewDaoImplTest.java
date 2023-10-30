package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.TestDataSourceProvider;
import com.airlines.jdbc.app.persistance.dao.CrewDao;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

class CrewDaoImplTest {

    private CrewDao crewDao;

    public void setUp() {
        DataSource h2DataSource = new TestDataSourceProvider().createDefaultInMemoryH2DataSource();
        // create
        // populate
        crewDao = new CrewDaoImpl(h2DataSource);
    }

    @Test
    void addNewCrewMemberToCrew() {
    }

    @Test
    void getListOfCrewMembersByCrewId() {
    }

    @Test
    void getListOfCrewMemberByCrewName() {
    }

    @Test
    void linkCrewMemberToCrew() {
    }
}