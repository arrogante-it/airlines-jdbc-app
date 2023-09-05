package com.airlines.jdbc.app.constants;

public class AirPlaneConstants {
    public static final String INSERT_AIRPLANE_SQL =
            "insert into airplane (code_name, model, manufacture_date, capacity, flight_range) values (?, ?, ?, ?, ?)";
    public static final String SELECT_BY_CODENAME_SQL =
            "select * from airplane where codeName = ?";
    public static final String SELECT_ALL_SQL =
            "select * from airplane";
    public static final String DELETE_AIRPLANE =
            "delete from airplane where id = ?";
    public static final String SELECT_AIRPLANE_BY_NAME =
            "select * from airplane where crew_id in (select id from crew where name = ?)";
    public static final String UPDATE_AIRPLANE =
            "update airplane set crew_id = ? where id = ?";
}
