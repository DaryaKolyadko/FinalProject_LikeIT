package com.kolyadko.likeit.type;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public enum StateType {
    ACTIVE,
    BANNED;

    public StateType getStateType(String value) {
        return valueOf(value.toUpperCase());
    }
}
