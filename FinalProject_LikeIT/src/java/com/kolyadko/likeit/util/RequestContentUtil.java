package com.kolyadko.likeit.util;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */
public class RequestContentUtil {
    private static final String CURRENT_USER_PARAM = "userContainer";

    public static boolean isCurrentUserAdmin(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && ((User) user.getObject()).isAdmin();
    }

    public static String getCurrentUserLogin(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user == null ? null : ((User) user.getObject()).getId();
    }
}