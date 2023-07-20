create table Airplane (
    id bigint auto_increment primary key,
    codeName varchar(255) not null,
    model varchar(255) not null,
    manufacture_date varchar(10) not null,
    capacity int not null,
    flight_range int not null,
    crew_id bigint,
    foreign key (crew_id) references Crew(id)
);

create table Crew (
    id bigint auto_increment primary key,
    name varchar(255) not null
);

create table CrewMember (
    id bigint auto_increment primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    position varchar(255) not null,
    birthday varchar(10) not null,
    citizenship varchar(255) not null
);

create table Crew_Crew_Member (
    crew_id bigint,
    crew_member_id bigint,
    primary key (crew_id, crew_member_id),
    foreign key (crew_id) references Crew(id),
    foreign key (crew_member_id) references CrewMember(id)
);