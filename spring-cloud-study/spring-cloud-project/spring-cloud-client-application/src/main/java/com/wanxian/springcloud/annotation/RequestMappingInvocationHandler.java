package com.wanxian.springcloud.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RequestMappingInvocationHandler implements InvocationHandler {
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final String serviceName;
    private BeanFactory beanFactory;

    public RequestMappingInvocationHandler(String serviceName, BeanFactory beanFactory) {
        this.serviceName = serviceName;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        GetMapping requestMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        if (requestMapping != null) {
            //得到requestMapping中的值
            String[] uri = requestMapping.value();
            //RestTemplate完整路径：http://${serviceName}/url?param1=value1&param2=value2
            StringBuilder urlBuilder = new StringBuilder("http://").append(serviceName).append("/").append(uri[0]);
            //获取方法参数的顺序
            int count = method.getParameterCount();
            //方法参数名称集合
            String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
            //方法参数类型集合
            Class<?>[] paramterTypes = method.getParameterTypes();
            //方法注解集合
            Annotation[][] annotations = method.getParameterAnnotations();//二维数组参数
            StringBuilder queryString = new StringBuilder();
            for (int i = 0; i < count; i++) {
                Class<?> paramterType = paramterTypes[i];
                Annotation[] paramAnnotations = annotations[i];

                RequestParam requestParam = (RequestParam) paramAnnotations[0];
                if (requestParam != null) {
                    String paramName = paramNames[i];
                    //当声明@RequestParam 以@RequestParam为主，否则
                    String requesetParamterName = StringUtils.hasText(requestParam.value()) ? requestParam.value() : paramName;
                    String requestParamValue = String.class.equals(paramterType) ?
                            (String) args[i] : String.valueOf(args[i]);
                    //uri ?name=value&age=value
                    queryString.append("&").append(requesetParamterName).append("=").append(requestParamValue);
                }
            }

            if (StringUtils.hasText(queryString)) { //参数不为空
                urlBuilder.append("?").append(queryString).toString();
            }
            String urlQuery = urlBuilder.toString();
            //获取RestTemplate，根据名称获取 loadBalanceRestTemplate
            RestTemplate lbRestTemplate = beanFactory.getBean("loadBalanceRestTemplate", RestTemplate.class);
            //执行restTemplate
            return lbRestTemplate.getForObject(urlQuery, method.getReturnType());
        }
        return null;
    }
}
