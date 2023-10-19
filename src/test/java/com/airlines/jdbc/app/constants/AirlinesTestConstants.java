package com.airlines.jdbc.app.constants;

public class AirlinesTestConstants {
    public static final String CREATE_CREW = "create table crew (\n" +
            "    id bigint auto_increment primary key,\n" +
            "    crew_name varchar(30) not null\n" +
            "); ";
    public static final String CREATE_AIRPLANE = "create table airplane (\n" +
            "    id bigint auto_increment primary key,\n" +
            "    code_name varchar(10) not null,\n" +
            "    model varchar(40) not null,\n" +
            "    manufacture_date varchar(10) not null,\n" +
            "    capacity int not null,\n" +
            "    flight_range int not null,\n" +
            "    crew_id bigint,\n" +
            "    foreign key (crew_id) references crew(id)\n" +
            ");";
    public static final String CREATE_CREW_MEMBER = "create table crew_member (\n" +
            "    id bigint auto_increment primary key,\n" +
            "    first_name varchar(15) not null,\n" +
            "    last_name varchar(25) not null,\n" +
            "    position varchar(255) not null,\n" +
            "    birthday varchar(10) not null,\n" +
            "    citizenship varchar(50) not null\n" +
            ");";
    public static final String CREATE_CREW_CREW_MEMBER = "create table crew_crew_member (\n" +
            "    crew_id bigint,\n" +
            "    crew_member_id bigint,\n" +
            "    primary key (crew_id, crew_member_id),\n" +
            "    foreign key (crew_id) references crew(id),\n" +
            "    foreign key (crew_member_id) references crew_member(id)\n" +
            ");";
}
