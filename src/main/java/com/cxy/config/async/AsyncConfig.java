//package com.cxy.config.async;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
///**
// * @program: template_project_mybatis_plus
// * @description: 异步线程池
// * @author: 陈翔宇
// * @create_time: 2020-11-21 10:10:09
// */
//@Configuration
//@EnableAsync
//public class AsyncConfig implements AsyncConfigurer {
//
//    /**
//     * 定义线程池
//     * @return
//     */
//    @Override
//    public Executor getAsyncExecutor() {
//        // 创建线程
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        // 核心数
//        taskExecutor.setCorePoolSize(10);
//        // 最大线程数
//        taskExecutor.setMaxPoolSize(30);
//        // 线程队列最大线程数
//        taskExecutor.setQueueCapacity(2000);
//        // 线程初始话
//        taskExecutor.initialize();
//        return taskExecutor;
//    }
//}
