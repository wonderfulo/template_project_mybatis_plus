package com.cxy.lambda;

import com.cxy.entity.User;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * ThreadLocal  可以保证每个线程中各自的变量无不干扰
 * 线程内部存储器
 */
public class ThreadLocalTest {

    static ThreadLocal<String> localVar = new ThreadLocal<>();

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :" + localVar.get());
        //清除本地内存中的本地变量
        localVar.remove();
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            //设置线程1中本地变量的值
            localVar.set("localVar1");
            //调用打印方法
            print("thread1");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());

        });

        Thread t2 = new Thread(() -> {
            //设置线程1中本地变量的值
            localVar.set("localVar2");
            //调用打印方法
            print("thread2");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());

        });

        t1.start();
        t2.start();
    }
}


class ThreadLocalTest2 {
    static ThreadLocal<DateFormat> localDF = ThreadLocal.withInitial(() -> new SimpleDateFormat("yy-MM-dd HH:mm:ss"));

    static void print(String str, Date date) {
        DateFormat dateFormat = localDF.get();
        System.out.println(dateFormat);
        System.out.println(str + " ：" + localDF.get().format(date));
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            //调用打印方法
            print("thread1", new Date());

        });

        Thread t2 = new Thread(() -> {
            //调用打印方法
            print("thread2", new Date());

        });

        t1.start();
        t2.start();

        Runnable helloWorld = () -> System.out.println("hello world");

        JButton button = new JButton();
        button.addActionListener(event ->
                System.out.println(event.getActionCommand()));
    }
}

interface IntPred {
    boolean test(Integer value);
}


class PredicateOver {
    boolean check(Predicate<Integer> predicate) {
        return false;
    }

//     boolean check(IntPred predicate) {
//        return false;
//    }

}

class PredicateTest2 {
    public static void main(String[] args) {
        PredicateOver predicateOver = new PredicateOver();
        //lombda 表达式在推断情形下，不能重载
        predicateOver.check(x -> x > 5);
    }
}

class StreamTest {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        users.add(user1);
        users.stream().filter(user -> {
            System.out.println(user);
            return user.equals("1");
        }).count();
    }
}
