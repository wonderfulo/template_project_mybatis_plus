package com.cxy;

import com.cxy.entity.User;
import com.cxy.mapper.UserMapper;
import com.cxy.service.mongodb.MongoDbSeniorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MongoDbSeniorService mongoDbSeniorService;


    /**
     * 正则表达式去html
     */
    @Test
    public void strListTest() {
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            strings.add((Math.ceil(i / 2)) + "");
        }

        List<String> listResult = strings.stream().distinct().collect(Collectors.toList());
        System.out.println(listResult);
    }

    /**
     * 正则表达式去html
     */
    @Test
    public void regexTest() {
        String str = "<span id=\"_calculateTextWidth\" style=\"visibility: hidden; font-size: 16px; display: inline-block; position: absolute; left: -100%; top: -100%;\">离线留言</span>";
        str += " 123</br>";
        String s = str.replaceAll("<[^>]+>", "");
        System.out.println(s);
    }
    /**
     * 正则表达式去空格
     */
    @Test
    public void regexTheBlankSpace() {
        String str = " 12 3 ";
        String s = str.replaceAll("\\s*|\\t|\\r|\\n", "");
        System.out.println(s);
    }


    @Test
    public void mongoDbTest() {
//        mongoDbSeniorService.insert();
        mongoDbSeniorService.insertMany();
    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }


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