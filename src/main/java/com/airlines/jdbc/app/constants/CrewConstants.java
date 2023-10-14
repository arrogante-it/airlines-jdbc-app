package com.airlines.jdbc.app.constants;

public class CrewConstants {
    public static final String INSERT_CREW_CREW_MEMBER =
            "insert into crew_crew_member (crew_id, crew_member_id) values (?, ?)";
    public static final String SELECT_CREW_MEMBERS_BY_ID =
            "select cm.* from crew_member cm join crew_crew_member cc on cm.id = cc.crew_member_id where cc.crew_id = ?";
    public static final String SELECT_CREW_MEMBERS_BY_CREW_NAME =
            "select cm.* from crewmember cm join crew_crewmember cc on cm.id = cc.crew_member_id " +
                    "join crew c on cc.crew_id = c.id where c.name = ?";
    public static final String INSERT_CREW_CREW_MEMBER_TO_CREW =
            "insert into crew_crew_member (crew_id, crew_member_id) values (?, ?)";
}
