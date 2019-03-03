package com.wanxian.spring.context;

import com.wanxian.demo.controller.TestController;
import com.wanxian.spring.annotation.Autowried;
import com.wanxian.spring.annotation.Controller;
import com.wanxian.spring.annotation.Service;
import com.wanxian.spring.aop.AopConfig;
import com.wanxian.spring.beans.BeanDefinition;
import com.wanxian.spring.beans.BeanPostProcessor;
import com.wanxian.spring.beans.BeanWrapper;
import com.wanxian.spring.context.support.BeanDefinitionReader;
import com.wanxian.spring.core.BeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {
    private String[] configLocation;
    private BeanDefinitionReader reader;
    //用来保证容器的单例
    private Map<String, Object> beanCacheMap = new HashMap<String, Object>();
    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, BeanWrapper>();

    public ApplicationContext(String... configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    public void refresh() {
        //定位
        this.reader = new BeanDefinitionReader(configLocation);
        //加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        //注册
        doRegisty(beanDefinitions);

        //依赖注入(lazy-init =false),执行依赖注入
        doAutoWried();

//
//        TestController testController = (TestController) getBean("testController");
//        testController.query(null, null, "wanxian");

    }

    private void doAutoWried() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()
        ) {

            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
        for (Map.Entry<String, BeanWrapper> beanWrapperEntry : this.beanWrapperMap.entrySet()) {

            populateBean(beanWrapperEntry.getKey(), beanWrapperEntry.getValue().getOriginalInstance());

        }
    }

    public void populateBean(String beanName, Object instance) {
        Class clazz = instance.getClass();
        //不是所有牛奶都叫特仑苏
        if (!(clazz.isAnnotationPresent(Controller.class) ||
                clazz.isAnnotationPresent(Service.class))) {
            return;
        }


        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowried.class)) {
                continue;
            }
            Autowried autowried = field.getAnnotation(Autowried.class);
            String autowriedBeanName = autowried.value().trim();
            if ("".equals(autowriedBeanName)) {
                autowriedBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                field.set(instance, this.beanWrapperMap.get(autowriedBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }


    //将beanDefinitions 注册到beanDefinitionMap中
    private void doRegisty(List<String> beanDefinitions) {
        try {
            for (String className : beanDefinitions) {
                //beanName有三种情况
                //1.默认类名首字母小写2.自定义名称3.接口注入
                Class<?> beanClass = Class.forName(className);

                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if (beanClass.isInterface()) {
                    continue;
                }
                BeanDefinition beanDefinition = reader.registerBean(className);
                if (beanDefinition != null) {
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                }
                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i :
                        interfaces) {
                    //如果多个实现类，只能覆盖，spring 会报错,可以自定义名字
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //依赖注入，从这里开始，通过读取BeanDefinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的OOP关系
    //2、我需要对它进行扩展，增强（为了以后AOP打基础）

    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        //String className = beanDefinition.getBeanClassName();
        try {
            Object instance = instantionBean(beanDefinition);
            BeanPostProcessor beanPostProcessor = new BeanPostProcessor();
            if (instance == null)
                return null;
            //实例初始化之前调用一次
            beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            BeanWrapper beanWrapper = new BeanWrapper(instance);
            beanWrapper.setAopConfig(instantionAopConfig(beanDefinition));
            this.beanWrapperMap.put(beanName, beanWrapper);
            //实例初始化之后调用一次
            beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            //populateBean(beanName, instance);
            return this.beanWrapperMap.get(beanName).getWrappedInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private AopConfig instantionAopConfig(BeanDefinition beanDefinition) throws Exception {
        AopConfig config = new AopConfig();
        String expression = reader.getConfig().getProperty("poinCut");
        String[] before = reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after = reader.getConfig().getProperty("aspectAfter").split("\\s");
        String className = beanDefinition.getBeanClassName();
        Pattern pattern = Pattern.compile(expression);
        Class<?> clazz = Class.forName(className);
        Class aspectClass = Class.forName(before[0]);
        for (Method m : clazz.getMethods()
        ) {
            if (pattern.matcher(m.toString()).matches()) {
                config.put(m, aspectClass.newInstance(),
                        new Method[]{aspectClass.getMethod(before[1]), aspectClass.getMethod(after[1])});
            }
        }
        return config;
    }


    private Object instantionBean(BeanDefinition beanDefinition) {
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = Class.forName(className);
            if (this.beanCacheMap.containsKey(className)) {
                instance = beanCacheMap.get(className);
            } else {
                instance = clazz.newInstance();
                this.beanCacheMap.put(className, instance);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
