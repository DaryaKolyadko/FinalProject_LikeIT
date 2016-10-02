package com.kolyadko.likeit.exception;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * ServiceException
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}