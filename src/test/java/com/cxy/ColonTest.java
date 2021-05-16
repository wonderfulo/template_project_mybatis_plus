package com.cxy;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ColonTest.java
 * @Description 冒号测试
 * @createTime 2021年05月15日 19:49:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ColonTest {

    @Test
    public void colon() {
        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3) {
                    continue outer;
                }
                System.out.printf("i: %s, j: %s\n", i, j);
            }
        }
        System.out.println("********************************************");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3) {
                    break;
                }
                System.out.printf("i: %s, j: %s\n", i, j);
            }
        }


    }

}
