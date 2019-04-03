//package com.wanxian.servlet;
//
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSessionActivationListener;
//import javax.servlet.http.HttpSessionEvent;
//import java.io.Serializable;
//import java.util.stream.Stream;
//
//@WebListener
//public class MyHttpSessionActivationListener implements HttpSessionActivationListener, Serializable {
//
//    private static final long serialVersionUID = 7212474891331256182L;
//
//
//    @Override
//    public void sessionWillPassivate(HttpSessionEvent se) {
//        Stream.of("MyHttpSessionActivationListener sessionWillPassivate" + se.getSession().getId()).forEach(System.out::println);
//    }
//
//    @Override
//    public void sessionDidActivate(HttpSessionEvent se) {
//        Stream.of("MyHttpSessionActivationListener sessionDidActivate" + se.getSession().getId()).forEach(System.out::println);
//    }
//}
