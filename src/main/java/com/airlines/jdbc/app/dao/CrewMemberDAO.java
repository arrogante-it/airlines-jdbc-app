package com.airlines.jdbc.app.dao;

import com.airlines.jdbc.app.entities.CrewMember;

import java.sql.SQLException;

public interface CrewMemberDAO {
    void saveCrewMember(CrewMember crewMember) throws SQLException;

    void updateCrewMember(CrewMember crewMember) throws SQLException;

    CrewMember findCrewMemberById(Long id) throws SQLException;
}
