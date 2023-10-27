package com.airlines.jdbc.app.constants;

public class AirlinesTestConstants {
    public static final String CREATE_CREW = "create table crew ( " +
            "    id bigint auto_increment primary key, " +
            "    crew_name varchar(30) not null " +
            "); ";
    public static final String CREATE_AIRPLANE = "create table airplane ( " +
            "    id bigint auto_increment primary key, " +
            "    code_name varchar(10) not null, " +
            "    model varchar(40) not null, " +
            "    manufacture_date varchar(10) not null, " +
            "    capacity int not null, " +
            "    flight_range int not null, " +
            "    crew_id bigint, " +
            "    foreign key (crew_id) references crew(id) " +
            ");";
    public static final String CREATE_CREW_MEMBER = "create table crew_member ( " +
            "    id bigint auto_increment primary key, " +
            "    first_name varchar(15) not null, " +
            "    last_name varchar(25) not null, " +
            "    position varchar(255) not null, " +
            "    birthday varchar(10) not null, " +
            "    citizenship varchar(50) not null " +
            ");";
    public static final String CREATE_CREW_CREW_MEMBER = "create table crew_crew_member ( " +
            "    crew_id bigint, " +
            "    crew_member_id bigint, " +
            "    primary key (crew_id, crew_member_id), " +
            "    foreign key (crew_id) references crew(id), " +
            "    foreign key (crew_member_id) references crew_member(id) " +
            ");";
}
