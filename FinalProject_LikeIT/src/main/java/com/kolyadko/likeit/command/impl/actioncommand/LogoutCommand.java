package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * Command lets log out from system
 */
public class LogoutCommand extends ActionCommand {
    public String execute(RequestContent content) {
        content.setSessionInvalidateFlag(true);
        return MappingManager.HOME_PAGE;
    }
}