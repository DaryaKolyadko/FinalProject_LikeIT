package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */

/**
 * Undefined command, throws an Exception on execution
 */
public class EmptyCommand implements Command {
    public String execute(RequestContent content) throws CommandException {
        throw new CommandException("No such command");
    }
}