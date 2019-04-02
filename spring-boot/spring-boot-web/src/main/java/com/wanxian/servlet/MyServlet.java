package com.wanxian.servlet;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "myServlet", urlPatterns = "/myServlet", initParams = {
        @WebInitParam(name = "myname", value = "myvalue")
})
public class MyServlet extends HttpServlet {

    private String value;

    @Autowired
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        value = servletConfig.getInitParameter("myname");
    }

    @Autowired
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        ServletContext servletContext = getServletContext();
        servletContext.log("doGet .........");
        writer.write("hello,world");

    }

}
