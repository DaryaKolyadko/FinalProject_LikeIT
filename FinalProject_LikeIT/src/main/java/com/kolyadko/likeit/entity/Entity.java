package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * Entity
 *
 * @param <T> entity id type
 */
public abstract class Entity<T> {
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}