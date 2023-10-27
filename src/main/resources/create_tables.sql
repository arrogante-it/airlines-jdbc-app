create table airlines_db.crew (
    id bigint auto_increment,
    crew_name varchar(40) not null,
    constraint crew_pk primary key (id)
);

create table airlines_db.airplane (
    id bigint auto_increment,
    code_name varchar(10) not null,
    model varchar(40) not null,
    manufacture_date date not null,
    capacity int not null,
    flight_range int not null,
    crew_id bigint,
    constraint airplane_pk primary key (id),
    constraint airplane_crew_fk foreign key (crew_id) references crew(id)
);

create table airlines_db.crew_member (
    id bigint auto_increment primary key,
    first_name varchar(40) not null,
    last_name varchar(60) not null,
    position varchar(255) not null,
    birthday date not null,
    citizenship varchar(50) not null,
    constraint crew_member primary key (id)
);

create table airlines_db.crew_crew_member (
    crew_id bigint,
    crew_member_id bigint,
    constraint crew_crew_member_pk primary key (crew_id, crew_member_id),
    constraint crew_crew_member_crew_fk foreign key (crew_id) references crew(id),
    constraint crew_crew_member_crew_member_fk foreign key (crew_member_id) references crew_member(id)
);