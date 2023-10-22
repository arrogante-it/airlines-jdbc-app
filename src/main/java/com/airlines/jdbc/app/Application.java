package com.airlines.jdbc.app;

import com.airlines.jdbc.app.persistance.dao.AirplaneDao;
import com.airlines.jdbc.app.persistance.dao.impl.AirplaneDaoImpl;
import com.airlines.jdbc.app.persistance.entities.Airplane;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        AirplaneDao airplaneDao = new AirplaneDaoImpl(DBConnector.createBasicDataSource());
        System.out.println("successfully connected to DB\n");

        // find all
        List<Airplane> airplanesAfter = airplaneDao.findAllAirplanes();
        System.out.print("ALL after update:\n");
        airplanesAfter.forEach(System.out::println);
    }
}
