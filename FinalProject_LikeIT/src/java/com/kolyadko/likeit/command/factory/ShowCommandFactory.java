package com.kolyadko.likeit.command.factory;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.EmptyCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowMainPageCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowUsersPageCommand;
import com.kolyadko.likeit.command.type.ShowCommandType;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowCommandFactory {

    public static Command getCommand(RequestContent content) {
        String requestURI = content.getRequestURI();
        return getCommand(requestURI);
    }

    public static Command getCommand(String requestURI) {
        Command command = new EmptyCommand();

        if (MappingManager.getInstance().getProperty(requestURI) == null) {
            return command;
        }

        ShowCommandType type = ShowCommandType.getCommandWithURI(requestURI);

        switch (type) {
            case SIMPLE_SHOW:
                return new ShowCommand(requestURI);
            case SHOW_HOME_PAGE:
                return new ShowMainPageCommand();
            case SHOW_USERS_PAGE:
                return new ShowUsersPageCommand();
            default:
                return command;
        }
    }
}