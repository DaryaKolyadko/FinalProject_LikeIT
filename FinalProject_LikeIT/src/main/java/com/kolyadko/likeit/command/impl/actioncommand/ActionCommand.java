package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.PermissionAccess;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.type.RoleType;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */

/**
 * Abstract class for action commands (which are sent by POST request)
 */
public abstract class ActionCommand implements Command, PermissionAccess {
    protected static final String SESSION_ATTR_INFO = "actionInfo";
    protected static final String SESSION_ATTR_ERROR = "actionError";

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, RoleType.ADMIN);
    }
}