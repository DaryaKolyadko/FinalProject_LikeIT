package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public abstract class Entity<T> {
    private T id;

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}