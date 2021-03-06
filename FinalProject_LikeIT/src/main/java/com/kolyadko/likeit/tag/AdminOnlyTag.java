package com.kolyadko.likeit.tag;

import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Created by DaryaKolyadko on 08.08.2016.
 */

/**
 * User tag for display data only for admins
 */
public class AdminOnlyTag extends BodyTagSupport {
    private static final String SESSION_ATTR_USER = "userContainer";

    @Override
    public int doStartTag() throws JspException {
        ObjectMemoryContainer user = (ObjectMemoryContainer) pageContext.getSession().getAttribute(SESSION_ATTR_USER);

        if (user != null) {
            User currentUser = (User) user.getObject();

            if (currentUser.isAdmin()) {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }
}