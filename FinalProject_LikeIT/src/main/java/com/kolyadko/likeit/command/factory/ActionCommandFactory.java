package com.kolyadko.likeit.command.factory;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.EmptyCommand;
import com.kolyadko.likeit.command.type.ActionCommandType;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * Creates ActionCommand objects
 */
public class ActionCommandFactory {
    private static final String PARAM_COMMAND = "command";
    private static final String EXCEPTION = "exceptionContainer";

    /**
     * Retrieve command name from request and creates ActionCommand object
     *
     * @param content request content
     * @return ActionCommand object
     */
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
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
        }

        return command;
    }
}