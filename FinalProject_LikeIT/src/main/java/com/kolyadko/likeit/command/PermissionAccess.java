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
    /**
     * Localized error message's key
     */
    String NOT_ALLOWED = "error.notAllowed";

    /**
     * Check by role if user is allowed to perform this action
     *
     * @param content request content
     * @param roles   roles of users who are allowed to perform this action
     * @return true - allowed<br>false - otherwise
     */
    default boolean allowedAction(RequestContent content, RoleType... roles) {
        boolean result = false;

        for (RoleType role : roles) {
            result = result || RequestContentUtil.checkCurrentUserRole(content, role);
        }

        return result;
    }

    /**
     * Check by login if user is allowed to perform this action
     *
     * @param content request content
     * @param users   logins of users who are allowed to perform this action
     * @return true - allowed<br>false - otherwise
     */
    default boolean allowedAction(RequestContent content, String... users) {
        boolean result = false;

        for (String user : users) {
            String current = RequestContentUtil.getCurrentUserLogin(content);
            result = result || (current != null && user != null && current.equals(user));
        }

        return result;
    }

    /**
     * Abstract method which check if this action is allowed
     *
     * @param content request content
     * @return true - allowed<br>false - otherwise
     */
    boolean isAllowedAction(RequestContent content);
}