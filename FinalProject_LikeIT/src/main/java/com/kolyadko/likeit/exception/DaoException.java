package com.kolyadko.likeit.exception;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * DaoException
 */
public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}