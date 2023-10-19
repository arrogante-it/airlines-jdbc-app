package com.airlines.jdbc.app.constants;

public class AirlinesConstants {
    public static final String INSERT_AIRPLANE_SQL =
            "insert into airplane (code_name, model, manufacture_date, capacity, flight_range, crew_id) values (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_BY_CODENAME_SQL =
            "select * from airplane where code_name = ?";
    public static final String SELECT_ALL_SQL =
            "select * from airplane";
    public static final String DELETE_AIRPLANE =
            "delete from airplane where id = ?";
    public static final String SELECT_AIRPLANE_BY_NAME =
            "select * from airplane where crew_id in (select id from crew where name = ?)";
    public static final String UPDATE_AIRPLANE =
            "update airplane set crew_id = ? where id = ?";

    public static final String SELECT_CREW_BY_ID = "select * from crew as c\n" +
            "left join crew_crew_member as ccm\n" +
            "on c.id = ccm.crew_id\n" +
            "left join crew_member as cm\n" +
            "on cm.id = ccm.crew_member_id\n" +
            "where c.id = ?";

//    public static final String INSERT_CREW_CREW_MEMBER =
//            "insert into crew_crew_member (crew_id, crew_member_id) values (?, ?)";
    public static final String SELECT_CREW_MEMBERS_BY_ID =
            "select cm.* from crew_member cm join crew_crew_member cc on cm.id = cc.crew_member_id where cc.crew_id = ?";
    public static final String SELECT_CREW_MEMBERS_BY_CREW_NAME =
            "select cm.* from crew_member cm join crew_crew_member cc on cm.id = cc.crew_member_id " +
                    "join crew c on cc.crew_id = c.id where c.name = ?";
    public static final String INSERT_CREW_CREW_MEMBER_TO_CREW =
            "insert into crew_crew_member (crew_id_foreign, crew_member_id_foreign) values (?, ?)";

    public static final String INSERT_CREW_MEMBER =
            "insert into crew_member (first_name, last_name, position, birthday, citizenship) values (?, ?, ?, ?, ?)";
    public static final String UPDATE_CREW_MEMBER =
            "update crew_member set first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? " +
                    "where id = ?";
    public static final String FIND_CREW_MEMBER_BY_ID =
            "select * from crew_member where id = ?";
}
