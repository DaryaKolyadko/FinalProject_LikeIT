package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */
public interface ShowEditPage {
    String SESSION_ATTR_ERROR = "actionError";

    default void setAsAttrIfNotAfterError(RequestContent content, Object object, String attrName) {
        Object afterError = content.getSessionAttribute(SESSION_ATTR_ERROR);

        if (afterError == null) {
            content.setRequestAttribute(attrName, object);
        }
    }
}