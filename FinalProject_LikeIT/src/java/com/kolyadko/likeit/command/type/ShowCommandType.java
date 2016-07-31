package com.kolyadko.likeit.command.type;

import com.kolyadko.likeit.command.impl.showcommand.ShowCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowHomePageCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowProfilePageCommand;
import com.kolyadko.likeit.command.impl.showcommand.ShowUserListPageCommand;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public enum ShowCommandType {
    SIMPLE_SHOW(null),
    SHOW_HOME_PAGE(new ShowHomePageCommand()),
    SHOW_USER_LIST_PAGE(new ShowUserListPageCommand()),
    SHOW_PROFILE_PAGE(new ShowProfilePageCommand());

    private ShowCommand showCommand;

    ShowCommandType(ShowCommand showCommand) {
        this.showCommand = showCommand;
    }

    public static ShowCommandType getCommandWithURI(String requestURI) {
        for (ShowCommandType type : ShowCommandType.values()) {
            if (type.showCommand != null) {
                if (type.showCommand.getPath().equals(requestURI)) {
                    return type;
                }
            }
        }

        return SIMPLE_SHOW;
    }

    public ShowCommand getShowCommand() {
        return showCommand;
    }
}