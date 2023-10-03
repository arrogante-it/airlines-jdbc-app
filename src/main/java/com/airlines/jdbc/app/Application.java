package com.airlines.jdbc.app;

import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.dao.impl.AirplaneDAOImpl;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.AirplaneModel;

import javax.sql.DataSource;
import java.time.LocalDate;

public class Application {
    private static DataSource dataSource;

    public static void main(String[] args) {
        AirplaneDAO airplaneDao = new AirplaneDAOImpl(dataSource);

        airplaneDao.saveAirplane(extractAirplaneModel());

        Airplane airplane;
        airplane = airplaneDao.findAirplaneByCode("123");

        System.out.println(airplane);
    }

    private static Airplane extractAirplaneModel() {
        return new Airplane.Builder()
                .id(1L)
                .codeName("123")
                .model(AirplaneModel.AIRBUS)
                .manufactureDate(LocalDate.now())
                .capacity(50)
                .flightRange(30)
                .build();
    }
}
