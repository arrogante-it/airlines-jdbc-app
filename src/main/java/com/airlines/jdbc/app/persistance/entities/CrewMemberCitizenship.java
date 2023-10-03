package com.airlines.jdbc.app.persistance.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CrewMemberCitizenship {
    UK("United Kingdom"),
    AUS("Australia"),
    UA("Ukraine"),
    SW("Switzerland"),
    MEX("Mexico");

    private final String name;
}
