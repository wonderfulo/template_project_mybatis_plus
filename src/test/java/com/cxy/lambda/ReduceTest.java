package com.cxy.lambda;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ReduceTest.java
 * @Description
 * @createTime 2021年04月20日 09:33:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ReduceTest {


    @Test
    public void reduceTest1(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        Integer integer = integers.stream().reduce(Integer::sum).get();
        System.out.println(integer);

        Integer integer1 = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println(integer1);

        int asInt = Arrays.stream(new int[]{1, 2, 3, 4}).reduce(Integer::sum).getAsInt();
        System.out.println(asInt);
    }

    @Test
    public void testReduce() {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});

        //求集合元素只和
        Integer result = stream.reduce(0, Integer::sum);
        System.out.println(result);

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        //求和
        stream.reduce(Integer::sum).ifPresent(System.out::println);

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //求最大值
        stream.reduce(Integer::max).ifPresent(System.out::println);
        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream.max(Comparator.comparingInt(x -> x)).ifPresent(System.out::println);

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //求最小值
        stream.reduce(Integer::min).ifPresent(System.out::println);

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //做逻辑
        stream.reduce((i, j) -> j < i ? j : i).ifPresent(System.out::println);

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        //求逻辑求乘机
        int result2 = stream.filter(i -> i % 2 == 0).reduce(1, (i, j) -> i * j);

        Optional.of(result2).ifPresent(System.out::println);
    }
}
