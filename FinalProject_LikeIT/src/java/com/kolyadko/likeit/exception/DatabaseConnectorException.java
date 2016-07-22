package com.kolyadko.likeit.exception;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public class DatabaseConnectorException extends Exception {
    public DatabaseConnectorException(String message) {
        super(message);
    }

    public DatabaseConnectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConnectorException(Throwable cause) {
        super(cause);
    }
}