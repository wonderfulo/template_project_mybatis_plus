package com.cxy.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: template_project_mybatis_plus
 * @description: redis测试类
 * @author: 陈翔宇
 * @create_time: 2020-11-08 10:26:02
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/stringAndHash")
    public Map<String,Object> testStringAndHash(){
        redisTemplate.opsForValue().set("age","26");
        Object age = redisTemplate.opsForValue().get("age");
        System.out.println(age);
        return null;
    }

}
