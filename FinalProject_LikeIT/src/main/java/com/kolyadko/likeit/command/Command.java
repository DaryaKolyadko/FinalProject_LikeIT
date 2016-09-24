package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * Command interface ("execution" behaviour)
 */
public interface Command {
    /**
     * Localized error message's key
     */
    String CHECK_INPUT_DATA = "error.checkInput";

    /**
     * Main command method (there are root actions inside it)
     *
     * @param content request content
     * @return real URL or mapping URL
     * @throws CommandException in case if some problems occurred
     */
    String execute(RequestContent content) throws CommandException;

    /**
     * Check if data which was set inside request is valid (returns true by default)
     *
     * @param content request content
     * @return true - data is valid<br>false - otherwise
     */
    default boolean isInputDataValid(RequestContent content) {
        return true;
    }
}