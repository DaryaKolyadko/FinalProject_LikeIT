package com.kolyadko.likeit.util;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.type.RoleType;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */
public class RequestContentUtil {
    private static final String CURRENT_USER_PARAM = "userContainer";

    public static boolean isCurrentUserAdmin(RequestContent content) {
        return checkCurrentUserRole(content, RoleType.ADMIN);
    }

    public static boolean checkCurrentUserRole(RequestContent content, RoleType role) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && ((User) user.getObject()).getRole() == role;
    }

    public static boolean isAuthenticated(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null;
    }

    public static boolean isActive(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user != null && ((User) user.getObject()).isActive();
    }

    public static String getCurrentUserLogin(RequestContent content) {
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(CURRENT_USER_PARAM);
        return user == null ? null : ((User) user.getObject()).getId();
    }

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

//    public static String getRequestParamsAsString(RequestContent content) {
//        StringBuilder stringBuilder = new StringBuilder("?");
//        ArrayList<String> stringValues = parametersMapToStringArray(content);
//        return String.join("&", stringValues);
//    }

//    private static ArrayList<String> parametersMapToStringArray(RequestContent content) {
//        ArrayList<String> paramsString = new ArrayList<>();
//        Map<String, String[]> params = content.getRequestParameters();
//
//        for (Object o : params.entrySet()) {
//            Map.Entry<String, Object> pair = (Map.Entry) o;
//            paramsString.add(pair.getKey() + "=" + ((String[]) pair.getValue())[0]);
//        }
//
//        return paramsString;
//    }
}