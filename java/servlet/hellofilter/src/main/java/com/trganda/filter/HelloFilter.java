package com.trganda.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;
import java.util.Enumeration;


@WebFilter(
    dispatcherTypes = {
            DispatcherType.REQUEST,
            DispatcherType.FORWARD,
            DispatcherType.INCLUDE,
            DispatcherType.ERROR
    },
    asyncSupported = true, urlPatterns = {"/"},
    initParams = {
            @WebInitParam(name = "name", value = "parameter", description = "description of name")
    },
    servletNames = {"HelloFilter"}
)
public class HelloFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.getOutputStream().println("Hello HelloFilter.");

        Enumeration<String> initPara = filterConfig.getInitParameterNames();
        while (initPara.hasMoreElements()) {
            servletResponse.getOutputStream().println(initPara.nextElement());
        }
    }
}
