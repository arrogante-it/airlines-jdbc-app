package com.airlines.jdbc.app.dao.impl;

import com.airlines.jdbc.app.dao.CrewDAO;
import com.airlines.jdbc.app.entities.Crew;
import com.airlines.jdbc.app.entities.CrewMember;

import java.sql.SQLException;
import java.util.List;

public class CrewDAOImpl implements CrewDAO {

    @Override
    public void addNewCrewMemberToCrew(Crew crew, CrewMember crewMember) throws SQLException {

    }

    @Override
    public List<CrewMember> getListOfCrewMembersByCrewId(Long crewId) throws SQLException {
        return null;
    }

    @Override
    public List<CrewMember> getListOfCrewMemberByCrewName(String crewName) throws SQLException {
        return null;
    }

    @Override
    public void linkCrewMemberToCrew(Long crewMemberId, Long crewId) throws SQLException {

    }
}
