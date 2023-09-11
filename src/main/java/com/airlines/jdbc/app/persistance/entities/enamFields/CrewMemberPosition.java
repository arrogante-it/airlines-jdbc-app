package com.airlines.jdbc.app.persistance.entities.enamFields;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CrewMemberPosition {
    CAP("Captain"),
    SER("Sergeant"),
    WO("Warrant officer"),
    LIE("Lieutenant"),
    COR("Corporal");

    private final String name;
}
