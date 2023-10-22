package com.airlines.jdbc.app.persistance.entities;

public enum CrewMemberCitizenship {
    UK("United Kingdom"),
    AUS("Australia"),
    UA("Ukraine"),
    SW("Switzerland"),
    MEX("Mexico");

    private final String name;

    CrewMemberCitizenship(String name) {
        this.name = name;
    }

    public static CrewMemberCitizenship fromString(String text) {
        for (CrewMemberCitizenship city : CrewMemberCitizenship.values()) {
            if (city.name.equalsIgnoreCase(text)) {
                return city;
            }
        }
        throw new IllegalArgumentException("No constant with name " + text + " found in CrewMemberCitizenship enum.");
    }

    public String getName() {
        return name;
    }
}
