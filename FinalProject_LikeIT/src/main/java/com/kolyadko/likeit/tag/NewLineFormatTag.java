package com.kolyadko.likeit.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by DaryaKolyadko on 08.08.2016.
 */
public class NewLineFormatTag extends BodyTagSupport {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(text.replace(System.getProperty("line.separator"), "<br>"));
        } catch (IOException e) {
            throw new JspException("Exception in NewLineFormatTag", e);
        }

        return SKIP_BODY;
    }
}