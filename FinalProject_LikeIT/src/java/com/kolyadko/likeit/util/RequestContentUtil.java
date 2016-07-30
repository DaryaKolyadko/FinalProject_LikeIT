package com.kolyadko.likeit.util;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */
public class RequestContentUtil {
    private static final String CURRENT_USER_PARAM = "user";

    public static boolean isCurrentUserAdmin(RequestContent content) {
        User user = (User) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && user.isAdmin();
    }
}