package com.airlines.jdbc.app.persistance.entities.enamFields;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AirplaneModel {
    BOEING("Boeing 777"),
    AIRBUS("Airbus A350"),
    MCDONNELL("McDonnell Douglas MD-11"),
    BOMBARDIER("Bombardier Dash 8"),
    GULFSTREAM("Gulfstream G550");

    private final String name;
}
