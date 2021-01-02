//package com.cxy.common;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * 命名为2 是为了解决 名称冲突的错误
// */
//@Component
//public class AppUtil2 implements ApplicationContextAware {
//
//    private static ApplicationContext ctx;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.ctx = applicationContext;
//    }
//
//    public static ApplicationContext getCtx() {
//        if(ctx==null)
//            throw new RuntimeException("spring 容器没有初始化！");
//        return ctx;
//    }
//}