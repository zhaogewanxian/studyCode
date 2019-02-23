package com.wanxian.spring.servlet;

import com.wanxian.demo.controller.*;
import com.wanxian.spring.annotation.Autowried;
import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.Service;
import com.wanxian.spring.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DispatchServlet extends HttpServlet {
    private Properties properties = new Properties();
    private Map<String, Object> beanMap = new ConcurrentHashMap<String, Object>();
    private List<String> classNames = new ArrayList<String>();
    private final String location = "contextConfigLocation";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("开始初始化：init");
        ApplicationContext applicationContext = new ApplicationContext(config.getInitParameter(location));

    }


    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
