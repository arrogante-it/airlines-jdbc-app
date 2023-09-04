package com.airlines.jdbc.app.constants;

public class AirPlaneConstants {
    public static final String INSERT_AIRPLANE_SQL =
            "insert into Airplane (code_name, model, manufacture_date, capacity, flight_range) values (?, ?, ?, ?, ?)";
    public static final String SELECT_BY_CODENAME_SQL =
            "select * from Airplane where codeName = ?";
    public static final String SELECT_ALL_SQL =
            "select * from Airplane";
    public static final String DELETE_AIRPLANE =
            "delete from Airplane where id = ?";
    public static final String SELECT_AIRPLANE_BY_NAME =
            "select * from Airplane where crew_id in (select id from Crew where name = ?)";
    public static final String UPDATE_AIRPLANE =
            "update Airplane set crew_id = ? where id = ?";
}
