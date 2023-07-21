# AirlinesJDBCApp

#### Persistence layer using JDBC API for an Airlines application.
 
##### Code is covered with unit tests with JUnit. 

## Install application:
- Clone application to your directory:
`git clone https://github.com/arrogante-it/airlines-jdbc-app.git`

- Build project: `mvn clean install`

## Prerequisite:
- JDK 11 or higher
- Maven 4.0.0 or higher

## Program main functional:

### AirPlaneDAO methods:
- saveAirPlane
- findAirPlaneByCode
- findAllAirplanes
- deleteAirPlane
- searchAirplanesByCrewName
- updateAirPlaneWithCrew

### CrewDAO methods:
- addNewCrewMemberToCrew
- getListOfCrewMembersByCrewId
- getListOfCrewMemberByCrewName
- linkCrewMemberToCrew

### CrewMemberDAO methods:
- saveCrewMember
- updateCrewMember
- findCrewMemberById