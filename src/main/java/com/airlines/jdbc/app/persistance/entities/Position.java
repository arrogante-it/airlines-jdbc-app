package com.airlines.jdbc.app.persistance.entities;

public enum Position {
    CAP("Captain"),
    SER("Sergeant"),
    WO("Warrant officer"),
    LIE("Lieutenant"),
    COR("Corporal"),
    STE("Steward"),
    PIL("Pilot"),
    SEC("Second Pilot");

    private final String name;

    Position(String name) {
        this.name = name;
    }

    public static Position fromString(String text) {
        for (Position position : Position.values()) {
            if (position.name.equalsIgnoreCase(text)) {
                return position;
            }
        }
        throw new IllegalArgumentException("No constant with name " + text + " found in Position enum.");
    }

    public String getName() {
        return name;
    }
}
