package com.cxy.interceptor;

import com.cxy.assert_package.AssertEx;
import com.cxy.constant.exception.MyEnumException;
import com.cxy.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
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
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("处理器前方法");
        String accessToken = request.getParameter("accessToken");
        if (StringUtils.isNotBlank(accessToken)) {
            log.debug("LoggingWebSocketServer 任务开始");
        }
        AssertEx.isNotEmpty(request.getParameter("accessToken"), MyEnumException.ACCESS_TOKEN_NO_EXIST);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
//        System.out.println("处理器后方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
//        System.out.println("处理器完成方法");
    }
}
