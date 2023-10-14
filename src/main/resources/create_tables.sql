drop table if exists airlines_db.crew_crew_member;
drop table if exists airlines_db.crew_member;
drop table if exists airlines_db.airplane;
drop table if exists airlines_db.crew;

create table airlines_db.crew (
    id bigint auto_increment primary key,
    name varchar(30) not null
);

create table airlines_db.airplane (
    id bigint auto_increment primary key,
    code_name varchar(10) not null,
    model varchar(40) not null,
    manufacture_date varchar(10) not null,
    capacity int not null,
    flight_range int not null,
    crew_id bigint,
    foreign key (crew_id) references crew(id)
);

create table airlines_db.crew_member (
    id bigint auto_increment primary key,
    first_name varchar(15) not null,
    last_name varchar(25) not null,
    position varchar(255) not null,
    birthday varchar(10) not null,
    citizenship varchar(50) not null
);

create table airlines_db.crew_crew_member (
    crew_id bigint,
    crew_member_id bigint,
    primary key (crew_id, crew_member_id),
    foreign key (crew_id) references crew(id),
    foreign key (crew_member_id) references crew_member(id)
);