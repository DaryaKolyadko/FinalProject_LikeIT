package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public abstract class ActionCommand implements Command {
    protected static final String SESSION_ATTR_INFO = "actionInfo";
    protected static final String SESSION_ATTR_ERROR = "actionError";
    protected static final String NOT_ALLOWED = "error.notAllowed";
    protected static final String CHECK_INPUT_DATA = "error.checkInput";

    protected boolean isInputDataValid(RequestContent content) {
        return true;
    }

    protected boolean allowedAction(RequestContent content, RoleType... roles) {
        boolean result = false;

        for (RoleType role : roles) {
            result = result || RequestContentUtil.checkCurrentUserRole(content, role);
        }

        return result;
    }

    protected boolean allowedAction(RequestContent content, String... users) {
        boolean result = false;

        for (String user : users) {
            String current = RequestContentUtil.getCurrentUserLogin(content);
            result = result || (current != null && user != null && current.equals(user));
        }

        return result;
    }

    protected boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, RoleType.ADMIN);
    }
}