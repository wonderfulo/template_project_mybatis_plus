package com.cxy.lambda;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ：陈翔宇
 * @date ：2020/11/30 9:14
 * @description：函数式编程测试
 * @modified By：
 * @version: $
 */
public class LambdaTest {
    public static void main(String[] args) {
//        Test.build(System.out::println);

        String name = getUserName();
        name = getUserName2();
        String finalName = name;
        Test.build(
                x -> System.out.println(x + finalName), "say:"
        );


        ModelService modelService = System.out::println;
        modelService.say("123");

        Predicate<Integer> predicate = x -> x > 5;
        boolean test = predicate.test(6);
        System.out.println(test);

        Function<Integer,String> function = integer -> String.valueOf(integer);
        String apply = function.apply(1);

        System.out.println(apply);


        new ThreadLocal<>();
    }

    static String getUserName() {
        return "cxy";
    }

    static String getUserName2() {
        return "cxy2";
    }
}


class Test {
    static void build(ModelService modelService, String say) {
        modelService.say(say);
    }
}