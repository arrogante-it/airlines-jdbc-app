package com.airlines.jdbc.app.constants;

public class CrewConstants {
    public static final String INSERT_CREW_CREW_MEMBER =
            "insert into Crew_crew_member (crew_id, crew_member_id) values (?, ?)";
    public static final String SELECT_CREW_MEMBERS_BY_ID =
            "select cm.* from CrewMember cm join Crew_crew_Member cc on cm.id = cc.crew_member_id where cc.crew_id = ?";
    public static final String SELECT_CREW_MEMBERS_BY_CREW_NAME =
            "select cm.* from CrewMember cm join Crew_CrewMember cc on cm.id = cc.crew_member_id " +
                    "join Crew c on cc.crew_id = c.id where c.name = ?";
    public static final String INSERT_CREW_CREW_MEMBER_TO_CREW =
            "insert into Crew_Crew_Member (crew_id, crew_member_id) values (?, ?)";
}
