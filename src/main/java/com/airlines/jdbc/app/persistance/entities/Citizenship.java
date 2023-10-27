package com.airlines.jdbc.app.persistance.entities;

public enum Citizenship {
    UK("United Kingdom"),
    AUS("Australia"),
    UA("Ukraine"),
    SW("Switzerland"),
    MEX("Mexico");

    private final String name;

    Citizenship(String name) {
        this.name = name;
    }

    public static Citizenship fromString(String text) {
        for (Citizenship city : Citizenship.values()) {
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
