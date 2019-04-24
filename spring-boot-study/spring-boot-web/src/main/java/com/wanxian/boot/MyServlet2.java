package com.wanxian.boot;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class MyServlet2 extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Writer writer = httpServletResponse.getWriter();
        ServletContext servletContext = getServletContext();
        servletContext.log("MyServlet2 doGet ....");
        writer.write("<html><body>hello,world MyServlet2 by spring boot</body></html>");
    }
}
