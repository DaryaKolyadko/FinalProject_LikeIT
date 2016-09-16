package com.kolyadko.likeit.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * Container for request data (used to prevent HttpRequest journey through the app)
 */
public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private String requestURI;
    private HttpSession session;

    public RequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractValues(request);
    }

    public void invalidateSession() {
        if (session != null) {
            session.invalidate();
            session = null;
        }
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

        requestURI = request.getRequestURI();
    }

    public String[] getRequestParameters(String parameterName) {
        return requestParameters.get(parameterName);
    }

    public String getRequestParameter(String parameterName) {
        return getRequestParameters(parameterName) != null ? getRequestParameters(parameterName)[0] : null;
    }

    public Map<String, String[]> getRequestParameters() {
        return Collections.unmodifiableMap(requestParameters);
    }

    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public Map<String, Object> getSessionAttributes() {
        return Collections.unmodifiableMap(sessionAttributes);
    }

    public void setRequestAttribute(String attributeName, Object attributeValue) {
        requestAttributes.put(attributeName, attributeValue);
    }

    public void setSessionAttribute(String attributeName, Object attributeValue) {
        sessionAttributes.put(attributeName, attributeValue);
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void insertValues(HttpServletRequest request) {
        Iterator iterator = requestAttributes.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
            request.setAttribute(pair.getKey(), pair.getValue());
        }

        iterator = sessionAttributes.entrySet().iterator();
        session = request.getSession(false);

        if (session != null) {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
                session.setAttribute(pair.getKey(), pair.getValue());
            }
        }
    }
}