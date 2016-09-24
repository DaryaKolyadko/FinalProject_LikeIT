package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.PermissionAccess;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */

/**
 * Abstract class for show commands (which are sent by GET request and recognized by URI)
 */
public abstract class ShowCommand implements Command, PermissionAccess {
    protected final static String ATTR_SERVER_ERROR = "serverError";

    private String path;

    public ShowCommand(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        return MappingManager.getInstance().getProperty(path);
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return true;
    }
}