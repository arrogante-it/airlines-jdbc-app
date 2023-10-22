package com.airlines.jdbc.app.persistance.dao;

import com.airlines.jdbc.app.persistance.entities.Airplane;
import com.airlines.jdbc.app.persistance.entities.Crew;

import java.util.List;

public interface AirplaneDao {
    void saveAirplane(Airplane airplane);

    Airplane findAirplaneByCode(String codeName);

    List<Airplane> findAllAirplanes();

    void deleteAirplane(Airplane airplane);

    List<Airplane> searchAirplanesByCrewName(String crewName);

    void updateAirplaneAndSetCrewId(Airplane airplane, Crew crew);
}
