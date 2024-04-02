package com.xxx.config;

import com.xxx.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.NotLinkException;

@Configuration//配置类
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public NoLoginInterceptor noLoginInterceptor(){
        return new NoLoginInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(noLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**","/index","/user/login");

    }
}
