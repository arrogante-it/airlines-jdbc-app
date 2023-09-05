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
- Save airplane
- Find airplane by code
- Find all airplanes
- Delete airplane
- Search airplanes by crew name
- Update airplane with crew

### CrewDAO methods:
- Add new crew member to crew
- Get list of crew members by crew id
- Get list of crew member by crew name
- Link crew member to crew

### CrewMemberDAO methods:
- Save crew member
- Update crew member
- Find crew member by id