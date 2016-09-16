package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * ShowEditPage interface (show edit smth. page after error so we shouldn't put initial object in request again)
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