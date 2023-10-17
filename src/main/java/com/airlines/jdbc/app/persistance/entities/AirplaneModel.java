package com.airlines.jdbc.app.persistance.entities;

public enum AirplaneModel {
    BOEING("Boeing 777"),
    AIRBUS("Airbus A350"),
    MCDONNELL("McDonnell Douglas MD-11"),
    BOMBARDIER("Bombardier Dash 8"),
    GULFSTREAM("Gulfstream G550");

    private final String name;

    AirplaneModel(String name) {
        this.name = name;
    }

    public static AirplaneModel fromString(String text) {
        for (AirplaneModel model : AirplaneModel.values()) {
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
