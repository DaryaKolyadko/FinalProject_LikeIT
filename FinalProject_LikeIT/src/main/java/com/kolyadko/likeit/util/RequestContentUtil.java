package com.kolyadko.likeit.util;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.type.RoleType;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */

/**
 * Util for RequestContent often performed actions
 */
public class RequestContentUtil {
    private static final String CURRENT_USER_PARAM = "userContainer";

    /**
     * Check if current user ia admin
     *
     * @param content RequestContent object
     * @return true - user is admin<br>false - user is not admin
     */
    public static boolean isCurrentUserAdmin(RequestContent content) {
        return checkCurrentUserRole(content, RoleType.ADMIN);
    }

    /**
     * Check if current user has specified role
     *
     * @param content RequestContent object
     * @param role    Role type
     * @return true - user has specified role<br>false - user has another role
     */
    public static boolean checkCurrentUserRole(RequestContent content, RoleType role) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && ((User) user.getObject()).getRole() == role;
    }

    /**
     * Check if user is authenticated
     *
     * @param content RequestContent object
     * @return true - authenticated user<br>false - not authenticated user
     */
    public static boolean isAuthenticated(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null;
    }

    /**
     * Check if user has @active@ state
     *
     * @param content RequestContent object
     * @return true - active user<br>false - banned user
     */
    public static boolean isActive(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && ((User) user.getObject()).isActive();
    }

    /**
     * Extract current user login from RequestContent
     *
     * @param content RequestContent object
     * @return current user login
     */
    public static String getCurrentUserLogin(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user == null ? null : ((User) user.getObject()).getId();
    }

    /**
     * Generate GET request query string
     *
     * @param content RequestContent object
     * @param params  param(s) name(s)
     * @return query string
     */
    public static String getParamAsQueryString(RequestContent content, String... params) {
        ArrayList<String> stringValues = new ArrayList<>();

        for (String param : params) {
            String value = content.getRequestParameter(param);

            if (param != null && value != null) {
                stringValues.add(param + "=" + value);
            }
        }

        return "?" + String.join("&", stringValues);
    }

    /**
     * Generate GET request query string
     *
     * @param paramName  param name
     * @param paramValue param value
     * @return query string
     */
    public static String generateQueryString(String paramName, Object paramValue) {
        return "?" + paramName + "=" + paramValue.toString();
    }
}