package com.cxy.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//该注解会启动springboot项目
@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void logTest() {
        logger.info("------------info--------------{}", 123);
        logger.error("------------error--------------{}", 123);
        logger.debug("------------debug--------------{}", 123);
        logger.trace("------------trace--------------{}", 123);
        logger.warn("------------warn--------------{}", 123);
    }

}