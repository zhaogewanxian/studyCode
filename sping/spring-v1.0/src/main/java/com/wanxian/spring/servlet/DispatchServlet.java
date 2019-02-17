package com.wanxian.spring.servlet;

import com.wanxian.spring.annotation.Autowried;
import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.Service;
import com.wanxian.demo.controller.*;

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
        //定位
        doLoadConfig(config.getInitParameter("contextConfigLocation"));


        //加载
        doScanner(properties.getProperty("scanPackage"));

        //注册
        doRegister();

        //注入

        doAutowired();

        TestController testController = (TestController) beanMap.get("testController");
        testController.query(null, null, "wanxian");

    }

    private void doAutowired() {
        if (beanMap.isEmpty()) return;

        for (Map.Entry<String, Object> entry : beanMap.entrySet()
        ) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowried.class)) {
                    continue;
                }
                Autowried autowried = field.getAnnotation(Autowried.class);
                String beanName = autowried.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), beanMap.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void doRegister() {
        if (classNames.isEmpty()) return;
        try {
            for (String className :
                    classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    //spring 中这个阶段不直接put 这里put的是BeanDefinition
                    beanMap.put(lowerFirstCase(clazz.getSimpleName()), clazz.newInstance()); //将name首字母改成小写
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);
                    String beanName = service.value();//设置了service注解的value
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }

                    Object instance = clazz.newInstance();
                    Class<?>[] interfaces = clazz.getInterfaces();

                    for (Class<?> i : interfaces
                    ) {
                        beanMap.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doScanner(String packName) {
        //URL url = this.getClass().getClassLoader().getResource(packName.replace("\\.", "\\"));//转换成文件路径，找到文件目录、文件
        URL url = this.getClass().getClassLoader().getResource(packName.replace(".", "/"));//转换成文件路径，找到文件目录、文件
        File classDir = new File(url.getFile());
        for (File file :
                classDir.listFiles()) {
            if (file.isDirectory()) { //如果为文件夹，继续遍历
                doScanner(packName + "." + file.getName());
            } else {
                classNames.add(packName + "." + file.getName().replace(".class", ""));
            }

        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(
                contextConfigLocation.replace("classpath:", ""));
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
