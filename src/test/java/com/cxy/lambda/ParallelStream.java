package com.cxy.lambda;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ParallelStream.java
 * @Description
 * @createTime 2021年04月19日 18:51:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ParallelStream {

    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            ArrayList<Object> objects = new ArrayList<>();
//        IntStream.range(1,100).forEach(objects::add);
            IntStream.range(1,100).parallel().forEach(objects::add);
            System.out.println(objects);
        }
    }
}
