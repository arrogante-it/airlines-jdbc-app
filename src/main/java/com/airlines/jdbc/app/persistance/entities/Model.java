package com.airlines.jdbc.app.persistance.entities;

public enum Model {
    BOEING("Boeing 777"),
    AIRBUS("Airbus A350"),
    MCDONNELL("McDonnell Douglas MD-11"),
    BOMBARDIER("Bombardier Dash 8"),
    GULFSTREAM("Gulfstream G550");

    private final String name;

    Model(String name) {
        this.name = name;
    }

    public static Model fromString(String text) {
        for (Model model : Model.values()) {
            if (model.name.equalsIgnoreCase(text)) {
                return model;
            }
        }
        throw new IllegalArgumentException("No constant with name " + text + " found in AirplaneModel enum.");
    }

    public String getName() {
        return name;
    }
}
