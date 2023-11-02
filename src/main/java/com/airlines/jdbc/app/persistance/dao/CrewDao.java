package com.airlines.jdbc.app.persistance.dao;

import com.airlines.jdbc.app.persistance.entities.Crew;
import com.airlines.jdbc.app.persistance.entities.CrewMember;

import java.util.List;

public interface CrewDao {
    void saveCrew(Crew crew);

    void linkCrewMemberToCrew(Long crewMemberId, Long crewId);

    List<CrewMember> getListOfCrewMembersByCrewId(Long crewId);

    List<CrewMember> getListOfCrewMembersByCrewName(String crewName);

    Crew findCrewById(Long id);
}
