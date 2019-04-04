package com.wanxian;

import com.wanxian.boot.MyFilter2;
import com.wanxian.boot.MyServlet2;
import com.wanxian.boot.MyServlet2RequestListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.wanxian.servlet")
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public static ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new MyServlet2());
        servletRegistrationBean.setName("myServlet2");
        servletRegistrationBean.addUrlMappings("/springboot/myServlet2", "/myServlet2");
        servletRegistrationBean.addInitParameter("myServlet2Name", "myServlet2Value");
        return servletRegistrationBean;
    }

    @Bean
    public static FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter2());
        filterRegistrationBean.addServletNames("myServlet2");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        return filterRegistrationBean;
    }

    @Bean
    public static ServletListenerRegistrationBean servletRequestListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MyServlet2RequestListener());
        return servletListenerRegistrationBean;
    }

    /**
     * @param builder
     * @return
     * @see ServerPropertiesAutoConfiguration
     * @see ServerProperties
     * 容器类型 servlet 1.tomcat 2.jetty 非servlet 3.Undertow
     * 激活servlet步骤(默认不支持,->非servlet容器Undertow)
     * 1.继承SpringBootServletInitializer
     * 2.重写#configure()方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
