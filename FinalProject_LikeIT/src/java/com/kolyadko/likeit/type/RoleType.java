package com.kolyadko.likeit.type;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public enum RoleType {
    USER,
    ADMIN;

    public RoleType getRoleType(String value) {
        return valueOf(value.toUpperCase());
    }
}