package com.wanxian.servlet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.stream.Stream;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        Stream.of("MyHttpSessionListener sessionCreated" + httpSessionEvent.getSession()).forEach(System.out::println);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Stream.of("MyHttpSessionListener sessionDestroyed" + httpSessionEvent.getSession()).forEach(System.out::println);
    }
}
