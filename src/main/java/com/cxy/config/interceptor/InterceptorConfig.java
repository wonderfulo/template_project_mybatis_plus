package com.cxy.config.interceptor;

import com.cxy.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: template_project_mybatis_plus
 * @description: 拦截器配置
 * @author: 陈翔宇
 * @create_time: 2020-11-21 10:32:52
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(new UserInterceptor());
        // 添加拦截路径规则
        ir.addPathPatterns("/async/*");
    }
}
