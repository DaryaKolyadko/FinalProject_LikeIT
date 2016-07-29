package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.type.ShowCommandType;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowUsersPageCommand extends ShowCommand {
    public ShowUsersPageCommand() {
        //TODO extract data from database
        super(ShowCommandType.SHOW_USERS_PAGE.getRequestURI());
    }
}