package com.kolyadko.likeit.exception;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * ConnectionPoolException
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}