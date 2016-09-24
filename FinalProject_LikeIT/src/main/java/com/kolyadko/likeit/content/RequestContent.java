package com.kolyadko.likeit.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * Container for request data (used to prevent HttpServletRequest journey through the app)
 */
public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private String requestURI;
    private boolean sessionInvalidateFlag;

    /**
     * Constructor which initializes RequestContent object with data from HttpServletRequest object
     * It extracts request attributes, request parameters, session attributes, requestURI and set
     * session invalidate flag as false
     *
     * @param request request object with data
     */
    public RequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractValues(request);
    }

    private void extractValues(HttpServletRequest request) {
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

        HttpSession session = request.getSession(false);

        if (session != null) {
            Enumeration<String> sessionAttributeNames = session.getAttributeNames();

            while (sessionAttributeNames.hasMoreElements()) {
                String sessionAttributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(sessionAttributeName, session.getAttribute(sessionAttributeName));
            }
        }

        requestURI = request.getRequestURI();
        sessionInvalidateFlag = false;
    }

    /**
     * Get request parameter values by name
     *
     * @param parameterName name of parameter that you want to get
     * @return parameter values
     */
    public String[] getRequestParameters(String parameterName) {
        return requestParameters.get(parameterName);
    }

    public String getRequestParameter(String parameterName) {
        return getRequestParameters(parameterName) != null ? getRequestParameters(parameterName)[0] : null;
    }

    /**
     * Get session attribute value by name
     *
     * @param attributeName name of attribute that you want to get
     * @return attribute value
     */
    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public Map<String, Object> getSessionAttributes() {
        return Collections.unmodifiableMap(sessionAttributes);
    }

    /**
     * Add new element in requestAttributes map
     *
     * @param attributeName  name of attribute that you want to get
     * @param attributeValue attribute value
     */
    public void setRequestAttribute(String attributeName, Object attributeValue) {
        requestAttributes.put(attributeName, attributeValue);
    }

    /**
     * Add new element in sessionAttributes map
     *
     * @param attributeName  attribute name
     * @param attributeValue attribute value
     */
    public void setSessionAttribute(String attributeName, Object attributeValue) {
        sessionAttributes.put(attributeName, attributeValue);
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setSessionInvalidateFlag(boolean sessionInvalidateFlag) {
        this.sessionInvalidateFlag = sessionInvalidateFlag;
    }

    /**
     * Insert all values from RequestContent object inside HttpServletRequest object
     *
     * @param request HttpServletRequest object you want fill with values
     */
    public void insertValues(HttpServletRequest request) {
        Iterator iterator = requestAttributes.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
            request.setAttribute(pair.getKey(), pair.getValue());
        }

        iterator = sessionAttributes.entrySet().iterator();
        HttpSession session = request.getSession(false);

        if (session != null) {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = (Map.Entry) iterator.next();
                session.setAttribute(pair.getKey(), pair.getValue());
            }

            if (sessionInvalidateFlag) {
                session.invalidate();
            }
        }
    }
}