package com.example.rs.config;

import com.example.rs.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //need to be intercepted
        String[] pathPatterns = {
                "/**"
        };

        //need not to be intercepted
        String[] excludePathPatterns = {
                "/error",
                "/login/**",
                "/login_page/**",
                "/bootstrap/**",
                "/Font-Awesome-4.5.0/**",
                "/css/**"
        };
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(pathPatterns).excludePathPatterns(excludePathPatterns);

    }
}