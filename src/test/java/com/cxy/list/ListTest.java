package com.cxy.list;

import com.cxy.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ListTest.java
 * @Description 集合测试
 * @createTime 2021年03月11日 14:56:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class ListTest {

    @Test
    public void listTest() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> strings2 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            strings.add(i + "");
        }

        for (int i = 6; i <= 15; i++) {
            strings2.add(i + "");
        }

        //取交集
//        strings.retainAll(strings2);

        //集合相加，不去重
//        strings.addAll(strings2);

        //方法一：去交集,集合相加, 相当于两集相加去重
        // 对于字符串有用，对于对象无用
//        strings.removeAll(strings2);
//        strings.addAll(strings2);

        //方法二, 使用了 treeSet 的集合不能有重复值，做去重操作，有点low
        strings.addAll(strings2);
        ArrayList<String> collect = strings.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(x -> x))), ArrayList::new)
        );

        ArrayList<String> collect1 = strings.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(x -> x))), ArrayList::new)
        );


        TreeSet<String> strings3 = new TreeSet<>();
        for (int i = 1; i <= 10; i++) {
            strings3.add(i + "");
        }

        for (int i = 6; i <= 15; i++) {
            strings3.add(i + "");
        }

        System.out.println("666");
    }


    /**
     * 去重测试
     */
    @Test
    public void duplicateRemovalTest() {
        System.out.println("取交集".length());
        ArrayList<User> userList1 = new ArrayList<>();
        ArrayList<User> userList2 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User userTemp = new User();
            userTemp.setUserName(i + "");
            userTemp.setAge(i);
            userList1.add(userTemp);
        }

        for (int i = 1; i <= 10; i++) {
            User userTemp = new User();
            userTemp.setUserName(i + "");
            userTemp.setAge(i * 2);
            userList2.add(userTemp);
        }
        //取交集
//        strings.retainAll(strings2);

        //集合相加，不去重
//        strings.addAll(strings2);

        //方法一：去交集,集合相加, 相当于两集相加去重
        // 对于字符串有用，对于对象无用
//        strings.removeAll(strings2);
//        strings.addAll(strings2);

        //方法二, 使用了 treeSet 的集合不能有重复值，做去重操作，有点low
        userList1.addAll(userList2);
        ArrayList<User> collect = userList1.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserName))), ArrayList::new)
        );

//        ArrayList<String> collect1 = strings.stream().collect(Collectors.collectingAndThen(
//                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(x -> x))), ArrayList::new)
//        );
        System.out.println(collect);


        TreeSet<String> strings3 = new TreeSet<>();
        for (int i = 1; i <= 10; i++) {
            strings3.add(i + "");
        }

        for (int i = 6; i <= 15; i++) {
            strings3.add(i + "");
        }

        System.out.println("666");
    }


    /**
     * 过滤器测试
     */
    @Test
    public void List() {
        ArrayList<Integer> intList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            intList.add(i);
        }
        List<Integer> collect = intList.stream().filter(x -> x >= 10).collect(Collectors.toList());
        System.out.println(collect);
    }


    /**
     * 交集测试
     */
    @Test
    public void List2() {
        ArrayList<Integer> intList = new ArrayList<>();
        ArrayList<Integer> intList2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            intList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            intList2.add(i);
        }
//        intList.retainAll(intList2);
        intList2.retainAll(intList);
        System.out.println(intList2);
    }
}
