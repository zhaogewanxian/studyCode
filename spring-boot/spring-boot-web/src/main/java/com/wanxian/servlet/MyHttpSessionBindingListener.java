package com.wanxian.servlet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener
public class MyHttpSessionBindingListener implements HttpSessionBindingListener {
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        ServletContext servletContext = event.getSession().getServletContext();
        servletContext.log("MyHttpSessionBindingListener valueBound ...");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        ServletContext servletContext = event.getSession().getServletContext();
        servletContext.log("MyHttpSessionBindingListener valueUnbound ...");
    }
}
