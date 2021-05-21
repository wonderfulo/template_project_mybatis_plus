package com.cxy.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: template_project_mybatis_plus
 * @description: 定时任务测试
 * @author: 陈翔宇
 * @create_time: 2020-11-21 19:03:15
 */
//@Component
public class ScheduleTest {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTest.class);

    //计数器
    int count1 = 1;
    int count2 = 1;

    // 每秒执行一次
    @Scheduled(fixedRate = 1000)
    @Async
    public void job1(){
        System.out.println("线程：" + Thread.currentThread().getName() + ",job1: 每秒执行一次，执行第【" + count1++ + "】次");
    }


    // 每秒执行一次
    @Scheduled(fixedRate = 1000)
    @Async
    public void job2(){
        System.out.println("线程：" + Thread.currentThread().getName() + ",job2: 每秒执行一次，执行第【" + count2++ + "】次");
    }



    // 每秒执行一次
//    @Scheduled(cron = "* * * * * ?", zone = "Asia/Shanghai")
    @Scheduled(fixedRate = 1000)
    @Async
    public void logTest(){

        logger.info("------------info--------------{}",123);
        logger.error("------------error--------------{}",123);
        logger.debug("------------debug--------------{}",123);
        logger.trace("------------trace--------------{}",123);
        logger.warn("------------warn--------------{}",123);
    }
}
