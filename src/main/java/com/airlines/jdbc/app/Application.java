package com.airlines.jdbc.app;

import com.airlines.jdbc.app.persistance.dao.AirplaneDAO;
import com.airlines.jdbc.app.persistance.dao.impl.AirplaneDAOImpl;
import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.AirplaneModel;
import com.airlines.jdbc.app.persistance.entities.Crew;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class Application {
    private static DataSource dataSource;

    public static void main(String[] args) {
        AirplaneDAO airplaneDao = new AirplaneDAOImpl(DBConnector.createBasicDataSource());
        System.out.println("successfully connected to DB\n");

        Crew crew = new Crew();
        crew.setId(101L);
        crew.setName("qq211");

        Airplane airplane  = new Airplane.Builder()
                .codeName("wwqweqw222")
                .model(AirplaneModel.MCDONNELL)
                .manufactureDate(LocalDate.now())
                .capacity(500)
                .flightRange(9000)
                .crew(crew)
                .build();

        airplaneDao.saveAirplane(airplane);

        // todo find
        System.out.println("BY CODE NAME:\n" + airplaneDao.findAirplaneByCode("QWE321") + "\n");
        // todo find
        System.out.println("BY CREW NAME:\n" + airplaneDao.searchAirplanesByCrewName("Black Tigers") + "\n");
//
//        // todo BEFORE UPDATE
//        // todo find all
//        List<Airplane> airplanesBefore = airplaneDao.findAllAirplanes();
//        System.out.print("ALL before update:\n");
//        airplanesBefore.forEach(System.out::println);
//        System.out.println("\n");
//
//        // todo update
//        Crew crew = new Crew();
//        crew.setId(1L);
//        crew.setName("Fight Club");
//        Airplane airplane = new Airplane.Builder()
//                .id(1L)
//                .codeName("ZXC322")
//                .model(AirplaneModel.BOMBARDIER)
//                .manufactureDate(LocalDate.now())
//                .capacity(700)
//                .flightRange(7000)
//                .crew(crew)
//                .build();
//        airplaneDao.updateAirplaneWithCrew(airplane, crew);
//
        // todo AFTER UPDATE
        // todo find all
        List<Airplane> airplanesAfter = airplaneDao.findAllAirplanes();
        System.out.print("ALL after update:\n");
        airplanesAfter.forEach(System.out::println);
    }

    public static Airplane getAirplaneInstance() {
        Crew crew = new Crew();
        crew.setName("qwe");

        return new Airplane.Builder()
                .codeName("QQQ506")
                .model(AirplaneModel.MCDONNELL)
                .manufactureDate(LocalDate.now())
                .capacity(500)
                .flightRange(9000)
                .crew(crew)
                .build();
    }
}
