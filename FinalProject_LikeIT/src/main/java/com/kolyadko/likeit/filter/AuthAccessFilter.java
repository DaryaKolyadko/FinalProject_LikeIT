package com.kolyadko.likeit.filter;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.RequestContentUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DaryaKolyadko on 19.07.2016.
 */

/**
 * Auth access filter (prevents access to Login and SignUp pages for not authenticated users)
 */
@WebFilter(filterName = "AuthAccessFilter")
public class AuthAccessFilter implements Filter {
    private static final String PARAM_REDIRECT_TO = "redirectTo";

    private String redirectTo;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (RequestContentUtil.isAuthenticated(new RequestContent(request))) {
            response.sendRedirect(request.getContextPath() + redirectTo);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        redirectTo = config.getInitParameter(PARAM_REDIRECT_TO);
    }
}