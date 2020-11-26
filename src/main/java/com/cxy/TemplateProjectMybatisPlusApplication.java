package com.cxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * @author admin
 */
@SpringBootApplication
@MapperScan("com.cxy.mapper")
//@EnableCaching
//@EnableWebSecurity
@EnableScheduling
public class TemplateProjectMybatisPlusApplication {

    @Autowired
    private RedisTemplate redisTemplate = null;


    public static void main(String[] args) {
        SpringApplication.run(TemplateProjectMybatisPlusApplication.class, args);
    }

    //自定义后初始化方法
    @PostConstruct
    public void init() {
        initRedisTemplate();
    }

    //设置redis的序列化器
    private void initRedisTemplate() {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
    }

}
