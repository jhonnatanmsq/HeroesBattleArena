package com.stefanini.hackaton.rest.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@WebFilter("/batalhar/*")
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if(httpServletRequest.getSession().getAttribute("USER") == null) {

            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso Negado!");

        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }

}