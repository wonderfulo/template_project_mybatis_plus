package com.cxy.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 获取的配置文件的属性
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GetConfigAtrr {

    @Value("${server.port}")
    private String port;

    @Autowired
    private Environment env;

    @Test
    public void getConfigAtrr(){
        String envPort = env.getRequiredProperty("server.port");
        System.out.println(port.equals(envPort));
    }
}
