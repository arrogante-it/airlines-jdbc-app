package com.airlines.jdbc.app.constants;

public class CrewMemberConstants {
    public static final String INSERT_CREW_MEMBER =
            "insert into crewmember (first_name, last_name, position, birthday, citizenship) values (?, ?, ?, ?, ?)";
    public static final String UPDATE_CREW_MEMBER =
            "update crewmember set first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? " +
                    "where id = ?";
    public static final String FIND_CREW_MEMBER_BY_ID =
            "select * from crewmember where id = ?";
}
