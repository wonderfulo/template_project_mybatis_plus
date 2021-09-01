package com.cxy.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName TaskExecutorConfig.java
 * @Description spring线程池配置类
 * @createTime 2021年09月01日 10:41:00
 */
@EnableAsync
@Component
public class TaskExecutorConfig {

    public static final String CONFIG_NAME = "com.cxy.config.TaskExecutorConfig";

    @Bean(name = TaskExecutorConfig.CONFIG_NAME)
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(50);
        // 设置队列容量
        executor.setQueueCapacity(100);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(600);
        // 设置默认线程名称
        executor.setThreadNamePrefix("async-om-pk-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
