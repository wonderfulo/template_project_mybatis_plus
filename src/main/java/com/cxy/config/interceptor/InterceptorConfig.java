package com.cxy.config.interceptor;

import com.cxy.interceptor.GlobalInterceptor;
import com.cxy.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
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
    //错误记录：如果请求的url路径没有控制层映射，则spring会再次请求（从拦截器来看，就是进入了两次全局拦截器）

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器遵守先注册先执行的原则
        //设置order后，遵守数字升序执行

        //定义全局拦截器
        registry.addInterceptor(new GlobalInterceptor())
                .order(1)
                .addPathPatterns("/**")       //拦截项目中的哪些请求
                .excludePathPatterns("/user/save");  //对项目中的哪些请求不拦截

        // 注册拦截器, 添加拦截路径规则
        registry.addInterceptor(new UserInterceptor())
                .order(2)
                .addPathPatterns("/user/*");

    }
}
