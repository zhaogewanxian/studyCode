package com.wanxian.spring.context.support;

import com.wanxian.spring.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionReader {
    private Properties config = new Properties();
    private List<String> registerBeanClass = new ArrayList<String>();
    private final String SCAN_PACKAGE = "scanPackage";

    public BeanDefinitionReader(String... locations) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(
                locations[0].replace("classpath:", ""));
        try {
            config.load(inputStream);
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
        doScanner(config.getProperty(SCAN_PACKAGE));

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
                registerBeanClass.add(packName + "." + file.getName().replace(".class", ""));
            }

        }

    }

    public List<String> loadBeanDefinitions() {
        return this.registerBeanClass;

    }

    //每注册一个className 返回一个BeanDefinition
    public BeanDefinition registerBean(String className) {
        if (this.registerBeanClass.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
            return beanDefinition;
        }

        return null;
    }

    public Properties getConfig() {
        return this.config;
    }

    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
