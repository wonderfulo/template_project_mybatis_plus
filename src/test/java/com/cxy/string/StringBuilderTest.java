package com.cxy.string;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.StringJoiner;
import java.util.stream.IntStream;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName StringBuilderTest.java
 * @Description
 * @createTime 2021年03月02日 09:21:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class StringBuilderTest {


    @Test
    public void stringBuildTest(){
        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add("cxy").add("zyl").add("cmy");
        System.out.println(sj);


//        String [] strings = new String[5];
//        for (int i = 0; i < 5; i++) {
//            strings[i] = i + "";
//        }
//
//        String s = StringUtils.join(strings, ",");
//        System.out.println(s);
    }
}
