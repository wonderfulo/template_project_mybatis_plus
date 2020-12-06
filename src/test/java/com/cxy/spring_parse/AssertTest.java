package com.cxy.spring_parse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName AssertTest.java
 * @Description TODO
 * @createTime 2020年12月06日 14:21:00
 */
@SpringBootTest
public class AssertTest {

    @Test
    public void assertTest(){
        // java junit 断言
//        Assert.assertNotNull("参数不能为空",null);
        // spring框架 定义的断言
        org.springframework.util.Assert.notNull(null,"属性不能为空");
    }
}
