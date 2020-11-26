package com.cxy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by liusijin on 16/3/30.
 */
@Component
public class AppUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public static ApplicationContext getCtx() {
        if(ctx==null)
            throw new RuntimeException("spring 容器没有初始化！");
        return ctx;
    }
}
