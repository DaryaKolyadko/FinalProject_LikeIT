package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 10.09.2016.
 */

/**
 * PermissionAccess interface ("confidential access" behaviour)
 */
public interface PermissionAccess {
    String NOT_ALLOWED = "error.notAllowed";

    default boolean allowedAction(RequestContent content, RoleType... roles) {
        boolean result = false;

        for (RoleType role : roles) {
            result = result || RequestContentUtil.checkCurrentUserRole(content, role);
        }

        return result;
    }

    default boolean allowedAction(RequestContent content, String... users) {
        boolean result = false;

        for (String user : users) {
            String current = RequestContentUtil.getCurrentUserLogin(content);
            result = result || (current != null && user != null && current.equals(user));
        }

        return result;
    }

    boolean isAllowedAction(RequestContent content);
}