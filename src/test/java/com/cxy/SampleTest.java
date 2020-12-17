package com.cxy;

import com.cxy.entity.User;
import com.cxy.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ServletContext servletContext;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
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