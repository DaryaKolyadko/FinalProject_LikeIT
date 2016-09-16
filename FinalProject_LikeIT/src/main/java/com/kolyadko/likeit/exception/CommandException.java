package com.kolyadko.likeit.exception;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * CommandException
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}