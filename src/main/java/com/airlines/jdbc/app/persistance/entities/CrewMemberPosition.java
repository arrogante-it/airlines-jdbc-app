package com.airlines.jdbc.app.persistance.entities;

public enum CrewMemberPosition {
    CAP("Captain"),
    SER("Sergeant"),
    WO("Warrant officer"),
    LIE("Lieutenant"),
    COR("Corporal");

    private final String name;

    CrewMemberPosition(String name) {
        this.name = name;
    }

    public static CrewMemberPosition fromString(String text) {
        for (CrewMemberPosition position : CrewMemberPosition.values()) {
            if (position.name.equalsIgnoreCase(text)) {
                return position;
            }
        }
        throw new IllegalArgumentException("No constant with name " + text + " found in CrewMemberPosition enum.");
    }

    public String getName() {
        return name;
    }
}
