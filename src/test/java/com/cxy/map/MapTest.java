package com.cxy.map;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName MapTest.java
 * @Description
 * @createTime 2021年03月30日 10:53:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class MapTest {

    @Test
    public void mapTest(){
        HashMap<String, String> map = new HashMap<>();
        map.put("1","1");

        String name = map.getOrDefault("2", "陈翔宇");
        System.out.println(name);
    }
}
