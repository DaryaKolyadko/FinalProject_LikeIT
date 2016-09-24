package com.kolyadko.likeit.listener;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.memorycontainer.MemoryContainer;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */

/**
 * Servlet request listener
 */
public class LikeItServletRequestListener implements ServletRequestListener {
    private static final String METHOD_GET = "GET";
    private static final String ATTR_URL = "originUrl";
    private static final String ATTR_LOCALE = "locale";

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

        if (request.getMethod().equalsIgnoreCase(METHOD_GET) && request.getParameter(ATTR_LOCALE) == null) {
            RequestContent requestContent = new RequestContent(request);
            Map<String, Object> sessionAttributes = requestContent.getSessionAttributes();

            for (Object o : sessionAttributes.entrySet()) {
                Map.Entry<String, Object> attr = (Map.Entry) o;

                if (attr.getValue() instanceof MemoryContainer) {
                    MemoryContainer container = (MemoryContainer) attr.getValue();

                    if (container.isOneOff()) {
                        request.getSession().removeAttribute(attr.getKey());
                    }
                }
            }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

        if (request.getMethod().equalsIgnoreCase(METHOD_GET)) {
            request.setAttribute(ATTR_URL, request.getRequestURI());
        }
    }
}