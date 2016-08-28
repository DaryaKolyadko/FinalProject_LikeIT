package com.kolyadko.likeit.tag;

import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Created by DaryaKolyadko on 08.08.2016.
 */
public class AuthenticatedOnlyTag extends BodyTagSupport {
    private static final String SESSION_ATTR_USER = "userContainer";

    @Override
    public int doStartTag() throws JspException {
        ObjectMemoryContainer user = (ObjectMemoryContainer) pageContext.getSession().getAttribute(SESSION_ATTR_USER);

        if (user != null) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}