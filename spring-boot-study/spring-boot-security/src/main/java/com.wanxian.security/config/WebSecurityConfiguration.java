package com.wanxian.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    //CSRF
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).requireCsrfProtectionMatcher(
                httpServletRequest -> httpServletRequest.getMethod().equals("POST")
        );
        //CSP headers 设置白名单
        http.headers().contentSecurityPolicy("script-src:https://code.jquery.com/");
        //x-frame-options  同域名是允许的
        http.headers().frameOptions().sameOrigin();
        //auth 任何请求都进入全部的认证
        //自定义参数
        http.authorizeRequests().anyRequest().fullyAuthenticated()
                .and().formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginProcessingUrl("index")
                .loginPage("/login")
                .failureForwardUrl("/error")
                .permitAll()
                .and().logout().permitAll();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("wanxian").password("123456").roles("admin");
    }

}
