package com.airlines.jdbc.app.dao;

import com.airlines.jdbc.app.entities.Crew;
import com.airlines.jdbc.app.entities.CrewMember;

import java.sql.SQLException;
import java.util.List;

public interface CrewDAO {
    void addNewCrewMemberToCrew(Crew crew, CrewMember crewMember) throws SQLException;

    List<CrewMember> getListOfCrewMembersByCrewId(Long crewId) throws SQLException;

    List<CrewMember> getListOfCrewMemberByCrewName(String crewName) throws SQLException;

    void linkCrewMemberToCrew(Long crewMemberId, Long crewId) throws SQLException;
}
