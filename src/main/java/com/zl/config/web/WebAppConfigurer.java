package com.zl.config.web;

import com.zl.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author tzxx
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer{

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).excludePathPatterns("/home/login","/coder/**","/chat","/sso/**","/static/**");
    }
}