package com.cxy.json;

import com.alibaba.fastjson.JSON;
import com.cxy.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName JsonTest.java
 * @Description
 * @createTime 2021年03月30日 10:52:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class JsonTest {

    /**
     * 基础类型 会有默认值
     */
    @Test
    public void fastJsonTest(){
        String str = "[{\"userName\":\"陈翔宇\",\"age\":1},{\"userName\":\"陈翔宇2\"}]";
        List<User> users = JSON.parseArray(str, User.class);
        Map<String, User> collect = users.stream().collect(Collectors.toMap(User::getUserName, y -> y));

        for (Map.Entry<String,User> entry : collect.entrySet()){
            User value = entry.getValue();
            value.setEmail("1");
        }
        System.out.println(users);
    }



}
