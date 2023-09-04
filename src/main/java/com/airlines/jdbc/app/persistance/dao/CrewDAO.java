package com.airlines.jdbc.app.persistance.dao;

import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;

import java.util.List;

public interface CrewDAO {
    void addNewCrewMemberToCrew(Crew crew, CrewMember crewMember);

    void linkCrewMemberToCrew(Long crewMemberId, Long crewId);

    List<CrewMember> getListOfCrewMembersByCrewId(Long crewId);

    List<CrewMember> getListOfCrewMemberByCrewName(String crewName);
}
