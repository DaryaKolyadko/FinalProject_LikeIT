package com.kolyadko.likeit.content;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private String requestURI;
    private HttpSession session;
    private ServletContext servletContext;

    public RequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractValues(request);
    }

    public void invalidateSession() {
        session.invalidate();
        session = null;
    }

    public void extractValues(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            requestParameters.put(parameterName, request.getParameterValues(parameterName));
        }

        session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();

        while (sessionAttributeNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributeNames.nextElement();
            sessionAttributes.put(sessionAttributeName, session.getAttribute(sessionAttributeName));
        }

        servletContext = request.getServletContext();
        requestURI = request.getRequestURI();
    }

    public String[] getRequestParameters(String parameterName) {
        return requestParameters.get(parameterName);
    }

    public String getRequestParameter(String parameterName) {
        return getRequestParameters(parameterName)[0];
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setRequestAttribute(String attributeName, Object attributeValue) {
        valueReplacer(requestAttributes, attributeName, attributeValue);
    }

    public void setSessionAttribute(String attributeName, Object attributeValue) {
        valueReplacer(sessionAttributes, attributeName, attributeValue);
    }

    public String getRequestURI() {
        return requestURI;
    }

    private <T> void valueReplacer(HashMap<String, T> map, String name, T value) {
        if (map.containsKey(name)) {
            map.replace(name, value);
        } else {
            map.put(name, value);
        }
    }

    public void insertValues(HttpServletRequest request) {
        Iterator iterator = requestAttributes.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
            request.setAttribute(pair.getKey(), pair.getValue());
        }

        iterator = sessionAttributes.entrySet().iterator();

        if (session != null) {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
                session.setAttribute(pair.getKey(), pair.getValue());
            }
        }
    }

    public void copyParamsToRequestAttributes() {
        for (Object o : requestParameters.entrySet()) {
            Map.Entry<String, Object> pair = (Map.Entry) o;
            requestAttributes.put(pair.getKey(), ((String[])pair.getValue())[0]);
        }
    }
}