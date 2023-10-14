package com.airlines.jdbc.app.persistance.dao.impl;

import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;
import static com.airlines.jdbc.app.persistance.entities.AirplaneModel.BOEING;
import com.airlines.jdbc.app.util.JdbcUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class AirplaneDaoTest {
    private static final LocalDate DATE = LocalDate.parse("2023-09-12");

    private static AirplaneDAO airplaneDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        DataSource h2DataSource = JdbcUtil.createDefaultInMemoryH2DataSource();
        createAccountTable(h2DataSource);
        airplaneDAO = new AirplaneDAOImpl(h2DataSource);
    }

    private static void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute("create table crew (\n" +
                    "    id bigint auto_increment primary key,\n" +
                    "    name varchar(30) not null\n" +
                    "); " +
                    "create table airplane (\n" +
                    "    id bigint auto_increment primary key,\n" +
                    "    code_name varchar(10) not null,\n" +
                    "    model varchar(40) not null,\n" +
                    "    manufacture_date varchar(10) not null,\n" +
                    "    capacity int not null,\n" +
                    "    flight_range int not null,\n" +
                    "    crew_id bigint,\n" +
                    "    foreign key (crew_id) references crew(id)\n" +
                    ");" +
                    "create table crew_member (\n" +
                    "    id bigint auto_increment primary key,\n" +
                    "    first_name varchar(15) not null,\n" +
                    "    last_name varchar(25) not null,\n" +
                    "    position varchar(255) not null,\n" +
                    "    birthday varchar(10) not null,\n" +
                    "    citizenship varchar(50) not null\n" +
                    ");" +
                    "create table crew_crew_member (\n" +
                    "    crew_id bigint,\n" +
                    "    crew_member_id bigint,\n" +
                    "    primary key (crew_id, crew_member_id),\n" +
                    "    foreign key (crew_id) references crew(id),\n" +
                    "    foreign key (crew_member_id) references crew_member(id)\n" +
                    ");");
        }
    }

    @Test
    public void shouldCorrectlySaveAirplane() {
        // by Setter
        Crew crew = new Crew();
        // crew.setId(1L); // ???
        crew.setName("Crew A");
        // by Builder
        Airplane airplane = new Airplane.Builder()
                .codeName("ABC123")
                .model(BOEING)
                .manufactureDate(DATE)
                .capacity(300)
                .flightRange(7000)
                .crew(crew)
                .build();

        int airplanesCountBeforeInsert = airplaneDAO.findAllAirplanes().size(); // 0
        airplaneDAO.saveAirplane(airplane);
        List<Airplane> airplanes = airplaneDAO.findAllAirplanes();

        assertNotNull(airplane.getId());
        assertEquals(airplanesCountBeforeInsert + 1, airplanes.size());
        assertTrue(airplanes.contains(airplane));
    }
}
