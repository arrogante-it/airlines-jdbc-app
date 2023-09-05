create table airplane (
    id bigint auto_increment primary key,
    code_name varchar(10) not null,
    model varchar(40) not null,
    manufacture_date varchar(10) not null,
    capacity int not null,
    flight_range int not null,
    crew_id bigint,
    foreign key (crew_id) references crew(id)
);
drop table if exists airplane;

create table crew (
    id bigint auto_increment primary key,
    name varchar(30) not null
);
drop table if exists crew;

create table crewmember (
    id bigint auto_increment primary key,
    first_name varchar(15) not null,
    last_name varchar(25) not null,
    position varchar(255) not null,
    birthday varchar(10) not null,
    citizenship varchar(50) not null
);
drop table if exists crewmember;

create table crew_crew_member (
    crew_id bigint,
    crew_member_id bigint,
    primary key (crew_id, crew_member_id),
    foreign key (crew_id) references crew(id),
    foreign key (crew_member_id) references crewmember(id)
);
drop table if exists crew_crew_member;