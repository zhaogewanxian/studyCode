package com.wanxian.springcloud.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class RestClientsRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware, EnvironmentAware {
    private BeanFactory beanFactory;
    private BeanFactoryAware beanFactoryAware;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassLoader classLoader = importingClassMetadata.getClass().getClassLoader();
        Map<String, Object> attributes =
                importingClassMetadata.getAnnotationAttributes(EnableRestClient.class.getName());

        //attributes->{clients}
        Class<?>[] clientClasses = (Class<?>[]) attributes.get("clients");

        // 接口类对象数组
        // 筛选所有接口
        Stream.of(clientClasses).filter(Class::isInterface) //过滤接口
                .filter(interfaceClass -> findAnnotation(interfaceClass, RestClient.class) != null)//选择@RestClient的接口
                .forEach(restClientClass -> {
                    //获取@RestClient元信息
                    RestClient restClient = findAnnotation(restClientClass, RestClient.class);
                    String serviceName = restClient.name();
                    //@RestClient接口变成jdk动态代理
                    Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{restClientClass},
                            new RequestMappingInvocationHandler(serviceName, beanFactory) {
                            });

                    registerBeanByFactoryBean(serviceName, proxy, restClientClass, registry);
                });

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private static void registerBeanByFactoryBean(String serviceName,
                                                  Object proxy, Class<?> restClientClass, BeanDefinitionRegistry registry) {

        String beanName = "RestClient." + serviceName;

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(RestClientClassFactoryBean.class);

        /**
         *  <bean class="User">
         *          <constructor-arg>${}</construtor-arg>
         *      </bean>
         */
        // 增加第一个构造器参数引用 : proxy
        beanDefinitionBuilder.addConstructorArgValue(proxy);
        // 增加第二个构造器参数引用 : restClientClass
        beanDefinitionBuilder.addConstructorArgValue(restClientClass);

        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.beanFactoryAware = beanFactoryAware;
    }

    private static class RestClientClassFactoryBean implements FactoryBean {

        private final Object proxy;

        private final Class<?> restClientClass;

        private RestClientClassFactoryBean(Object proxy, Class<?> restClientClass) {
            this.proxy = proxy;
            this.restClientClass = restClientClass;
        }

        @Override
        public Object getObject() throws Exception {
            return null;
        }

        @Override
        public Class<?> getObjectType() {
            return null;
        }
    }
}
