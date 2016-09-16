package com.kolyadko.likeit.command.factory;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.EmptyCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowDefaultContentCommand;
import com.kolyadko.likeit.command.type.ShowCommandType;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */

/**
 * Creates ShowCommand objects
 */
public class ShowCommandFactory {
    /**
     * Retrieves requestUri from request and generates ShowCommand
     *
     * @param content request content
     * @return ShowCommand object
     */
    public static Command getCommand(RequestContent content) {
        String requestURI = content.getRequestURI();
        return getCommand(requestURI);
    }

    /**
     * Based on URI creates ShowCommand\Command object
     *
     * @param requestURI requested URI
     * @return Command object
     */
    public static Command getCommand(String requestURI) {
        Command command = new EmptyCommand();

        if (MappingManager.getInstance().getProperty(requestURI) == null) {
            return command;
        }

        ShowCommandType type = ShowCommandType.getCommandWithURI(requestURI);

        if (type == ShowCommandType.SIMPLE_SHOW) {
            return new ShowDefaultContentCommand(requestURI);
        }

        return type.getShowCommand();
    }
}