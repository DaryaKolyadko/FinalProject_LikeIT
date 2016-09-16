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
    String CHECK_INPUT_DATA = "error.checkInput";

    String execute(RequestContent content) throws CommandException;

    default boolean isInputDataValid(RequestContent content) {
        return true;
    }
}