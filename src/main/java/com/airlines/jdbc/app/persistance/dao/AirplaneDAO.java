package com.airlines.jdbc.app.persistance.dao;

import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;

import java.util.List;

public interface AirplaneDAO {
    void saveAirplane(Airplane airplane);

    void deleteAirplane(Airplane airplane);

    void updateAirplaneWithCrew(Airplane airplane, Crew crew);

    Airplane findAirplaneByCode(String codeName);

    List<Airplane> findAllAirplanes();

    List<Airplane> searchAirplanesByCrewName(String crewName);
}
