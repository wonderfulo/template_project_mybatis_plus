package com.cxy;

import com.cxy.entity.User;
import com.cxy.mapper.SysUserMapper;
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

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * concatWsTest
     */
    @Test
    public void strToDoule() {
        String a = "6.00";
        Double d= Double.parseDouble(a);
        System.out.println(d);
    }

    /**
     * concatWsTest
     */
    @Test
    public void concatWsTest() {
        List<String> listTest = sysUserMapper.getConcatWsTest();
        System.out.println(123);
    }



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




}