package com.airlines.jdbc.app.dao;

import com.airlines.jdbc.app.entities.Airplane;
import com.airlines.jdbc.app.entities.Crew;

import java.sql.SQLException;
import java.util.List;

public interface AirPlaneDAO {
    void saveAirplane(Airplane airplane) throws SQLException;

    Airplane findAirplaneByCode(String codeName) throws SQLException;

    List<Airplane> findAllAirplanes() throws SQLException;

    void deleteAirplane(Airplane airplane) throws SQLException;

    List<Airplane> searchAirplanesByCrewName(String crewName) throws SQLException;

    void updateAirplaneWithCrew(Airplane airplane, Crew crew) throws SQLException;
}
