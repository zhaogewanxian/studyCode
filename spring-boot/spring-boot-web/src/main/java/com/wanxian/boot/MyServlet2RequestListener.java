package com.wanxian.boot;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class MyServlet2RequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletContext servletContext = sre.getServletContext();
        servletContext.log("MyServlet2RequestListener by spring boot destroyed ....");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletContext servletContext = sre.getServletContext();
        servletContext.log("MyServlet2RequestListener by spring boot is  initialized ....");
    }
}
