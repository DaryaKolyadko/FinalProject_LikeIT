package com.kolyadko.likeit.command.factory;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.EmptyCommand;
import com.kolyadko.likeit.command.type.ActionCommandType;
import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public class ActionCommandFactory {
    private static final String PARAM_COMMAND = "command";
    private static final String WRONG_COMMAND_ATTR = "wrongCommand";

    public static Command getCommand(RequestContent content) {
        Command command = new EmptyCommand();
        String commandName = content.getRequestParameter(PARAM_COMMAND);

        if (commandName == null || commandName.isEmpty()) {
            return command;
        }

        try {
            ActionCommandType type = ActionCommandType.valueOf(commandName);
            command = type.getCommand();
        } catch (IllegalArgumentException e) {
            content.setRequestAttribute(WRONG_COMMAND_ATTR, commandName);
        }

        return command;
    }
}