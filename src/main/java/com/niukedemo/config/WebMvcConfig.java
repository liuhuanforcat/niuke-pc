package com.niukedemo.config;

import com.niukedemo.controller.interceptor.DataInterceptor;
import com.niukedemo.controller.interceptor.LoginRequiredInterceptor;
import com.niukedemo.controller.interceptor.LoginTicketInterceptor;
import com.niukedemo.controller.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 刘欢
 * @date 2022年10月12日 19:41
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
//    @Autowired
//    private LoginRequiredInterceptor loginRequiredInterceptor;
    @Autowired
    private MessageInterceptor messageInterceptor;
    @Autowired
    private DataInterceptor dataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor).excludePathPatterns("/**/*.css",
                "/**/*.js","/**/*.png","/**/*.jpg","jpeg");
//        registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/**/*.css",
//                "/**/*.js","/**/*.png","/**/*.jpg","jpeg");
        registry.addInterceptor(messageInterceptor).excludePathPatterns("/**/*.css",
                "/**/*.js","/**/*.png","/**/*.jpg","jpeg");
        registry.addInterceptor(dataInterceptor).excludePathPatterns("/**/*.css",
                "/**/*.js","/**/*.png","/**/*.jpg","jpeg");
    }
}
