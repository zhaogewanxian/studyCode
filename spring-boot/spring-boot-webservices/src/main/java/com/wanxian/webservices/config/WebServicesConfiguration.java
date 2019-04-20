package com.wanxian.webservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * web services 配置类
 *
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
 * @see org.springframework.ws.config.annotation.WsConfigurerAdapter
 */
@Configuration
public class WebServicesConfiguration extends WsConfigurerAdapter {
    @Bean("user")
    @Autowired
    public Wsdl11Definition userWsdl11Definition(XsdSchema userxSchema) {
        Wsdl11Definition userWsdl11Definition = new DefaultWsdl11Definition();
        ((DefaultWsdl11Definition) userWsdl11Definition).setPortTypeName("UserServicePort");
        ((DefaultWsdl11Definition) userWsdl11Definition).setLocationUri("/web-services");
        ((DefaultWsdl11Definition) userWsdl11Definition).setTargetNamespace("http://www.rockrolltime.cn/schema");
        ((DefaultWsdl11Definition) userWsdl11Definition).setSchema(userxSchema);
        return userWsdl11Definition;
    }

    @Bean
    public XsdSchema userXsdShema() {
        return new SimpleXsdSchema(new ClassPathResource("META-INF/schemas/user.xsd"));
    }

}
