package com.kolyadko.likeit.type;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public enum GenderType {
    MALE,
    FEMALE,
    OTHER;

    public static GenderType getGenderType(String value) {
        return value != null ? valueOf(value.toUpperCase()) : null;
    }
}