package com.ral.manages.comms.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCorsFilter implements Filter {

    private static final String chartName = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CorsFilter初始化中");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding(chartName);
        /*response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Credentials","true");*/
        chain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("CorsFilter销毁中");
    }
}
