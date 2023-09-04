package com.airlines.jdbc.app.persistance.dao;

import com.airlines.jdbc.app.persistance.entities.CrewMember;

public interface CrewMemberDAO {
    void saveCrewMember(CrewMember crewMember);

    void updateCrewMember(CrewMember crewMember);

    CrewMember findCrewMemberById(Long id);
}
