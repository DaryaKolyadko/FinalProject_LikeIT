package com.kolyadko.likeit.tag;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by DaryaKolyadko on 08.08.2016.
 */

/**
 * User tag for displaying a new line symbol in comments and questions textAreas
 */
public class HtmlTextFormatTag extends BodyTagSupport {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            text = StringEscapeUtils.escapeHtml4(text);
            out.write(text.replace(System.getProperty("line.separator"), "<br>"));
        } catch (IOException e) {
            throw new JspException("Exception in HtmlTextFormatTag", e);
        }

        return SKIP_BODY;
    }
}