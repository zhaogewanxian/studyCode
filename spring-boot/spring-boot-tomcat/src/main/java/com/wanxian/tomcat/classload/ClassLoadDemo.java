package com.wanxian.tomcat.classload;

public class ClassLoadDemo {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        while (true) {
            System.out.println(classLoader.getClass().getName());
            classLoader = classLoader.getParent();
            if (classLoader == null) break;//bootstrap是null，rt 包 jre
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.printf(systemClassLoader.getClass().getName()); //SystemClassLoader =AppClassLoader
    }
}
