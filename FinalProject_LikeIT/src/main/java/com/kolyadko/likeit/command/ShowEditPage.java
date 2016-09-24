package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * ShowEditPage interface (show edit smth. page after error so we shouldn't put initial object in request again)
 */
public interface ShowEditPage {
    /**
     * session attribute name which contains error text in case of error on edit smth. page
     */
    String SESSION_ATTR_ERROR = "actionError";

    /**
     * Set object which you want to edit as session attribute in case if it is first Edit...Page loading.
     * In case if it is reloading of this page because of errors in edit form, original object doesn't
     * placed in session (P.S. the Uncompleted...MemoryContainer does but not in this method)
     *
     * @param content  request content
     * @param object   object that you want to edit
     * @param attrName attribute's name which will contain your object
     */
    default void setAsAttrIfNotAfterError(RequestContent content, Object object, String attrName) {
        Object afterError = content.getSessionAttribute(SESSION_ATTR_ERROR);

        if (afterError == null) {
            content.setRequestAttribute(attrName, object);
        }
    }
}