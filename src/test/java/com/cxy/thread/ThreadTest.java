package com.cxy.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ThreadTest.java
 * @Description
 * @createTime 2021年02月03日 18:27:00
 */

//该注解会启动springboot项目
@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ThreadTest {

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(10, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static ArrayList<String> strings = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {
            strings.add(i + "");
        }
    }



    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Test
    public void testThread() {
        //循环标志

        AtomicInteger i = new AtomicInteger();

        String s = strings.get(i.get());
        if (StringUtils.isNotBlank(s)) {

            pool.execute(() -> {

                boolean dataFlag = true;

                while (dataFlag){

                    int i1 = i.addAndGet(1);
                    String s1 = "";
                    try {
                        s1 = strings.get(i1);
                    } catch (RuntimeException e) {
                        dataFlag = false;
                    }

                    System.err.println("s1: " + s1);

                    if (StringUtils.isNotBlank(s1)) {
                        dataFlag = true;
                    } else {
                        dataFlag = false;
                    }
                }


            });
        }
    }

    @Test
    public void ThreadPoolTaskExecutorTest(){
        taskExecutor.execute(() -> {
            System.out.println(111);
            System.out.println(222);
            System.out.println(33);
        });
        taskExecutor.execute(() -> {
            System.out.println(444);
            System.out.println(555);
            System.out.println(666);
        });
        System.out.println(999);
    }
}
