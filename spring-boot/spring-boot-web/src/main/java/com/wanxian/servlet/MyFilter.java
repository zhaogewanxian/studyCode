package com.wanxian.servlet;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 可以映射servletName和多个urlPatterns
 */
@WebFilter(servletNames = "myServlet",urlPatterns = "/myservlet")
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServletContext servletContext = httpServletRequest.getServletContext();
        servletContext.log("myFilter  doFilterInternal....");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
