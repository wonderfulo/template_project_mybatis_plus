package com.cxy.exception;

import com.cxy.assert_package.AssertEx;
import com.cxy.constant.exception.DisplayTaskEnumException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ExceptionTest.java
 * @Description
 * @createTime 2021年05月14日 10:29:00
 */
//该注解会启动springboot项目
@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ExceptionTest {

    @Test
    public void Test(){
        AssertEx.isNotEmpty(0L, DisplayTaskEnumException.ID_NOT_EXIST);
    }

}
