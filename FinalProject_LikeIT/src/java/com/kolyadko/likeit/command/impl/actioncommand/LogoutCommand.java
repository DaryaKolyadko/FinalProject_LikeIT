package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public class LogoutCommand implements Command {
    public String execute(RequestContent content) {
        content.invalidateSession();
        return MappingManager.HOME_PAGE;
    }
}