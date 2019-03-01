package com.leven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author 孙乐进
 * @date 2019/2/25
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/index.html");
        registry.addViewController("/lvx/login").setViewName("/common/login.html");
        registry.addViewController("/login").setViewName("/common/login.html");
    }

}
