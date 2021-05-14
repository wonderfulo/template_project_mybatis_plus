package com.cxy.utils.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @createTime 2021年05月14日 09:45:00
 */
public class AppUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static ApplicationContext getCtx() {
        if(ctx==null) {
            throw new RuntimeException("spring 容器没有初始化！");
        }
        return ctx;
    }
}
