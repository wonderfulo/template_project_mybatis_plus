package com.cxy.schedule;

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
}
