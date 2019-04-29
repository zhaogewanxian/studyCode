package com.wanxian.starter.configuration;

import com.wanxian.starter.domain.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Person} 自动装配
 */
//@Configuration
@ConditionalOnWebApplication //web应用
@ConditionalOnProperty(prefix = "person", name = "enabled", havingValue = "true") //条件装配
//@EnableConfigurationProperties(Person.class)
public class PersonAutoConfiguration {

    @ConfigurationProperties(prefix = "person")
    @Bean
    public Person person() {
        return new Person();
    }
}
