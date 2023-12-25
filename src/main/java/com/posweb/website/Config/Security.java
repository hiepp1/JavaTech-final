package com.posweb.website.Config;

import com.posweb.website.Service.RoleService;
import com.posweb.website.Service.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Security implements WebMvcConfigurer {

    @Autowired
    private RoleService roleService;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/salesperson/**");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(roleService).addPathPatterns("/admin/**");
    }
}