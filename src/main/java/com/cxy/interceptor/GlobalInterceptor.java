package com.cxy.interceptor;

import com.cxy.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: template_project_mybatis_plus
 * @description: 全局拦截器
 * @author: 陈翔宇
 * @create_time: 2020-11-15 12:21:44
 */
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        System.out.println("处理器前方法");
//        String accessToken = request.getParameter("accessToken");
//        if (StringUtils.isEmpty(accessToken)){
//            throw new MyException("accessToken不能为空");
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
//        System.out.println("处理器后方法");
    }

    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
//        System.out.println("处理器完成方法");
    }
}
