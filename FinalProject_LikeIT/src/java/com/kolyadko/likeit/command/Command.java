package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public interface Command {
    String execute(RequestContent content) throws CommandException;
}