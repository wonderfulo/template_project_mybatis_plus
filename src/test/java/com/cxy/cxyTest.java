package com.cxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxy.common.JsonResponse;
import com.cxy.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName cxyTest.java
 * @Description
 * @createTime 2021年03月04日 14:01:00
 */
public class cxyTest {

    public static void main(String[] args) {
//        String htmlUrl = "http://it.xxynet.com/h5-mb-collections/displayGuild/index.html#/swithPage?accessToken=$access_token&state=detail/displayTaskId:1080/shopId:5006982630/shopName:$shop_name/shopTaskStatus:4/reportNum:$report_num";
//        htmlUrl = htmlUrl.replace("$shop_name", "666");
//        System.out.println(htmlUrl);
//        System.out.println($shop_name);


//        System.out.println(Boolean.FALSE.toString());

//        A a = new A().build("张三").build(18).build(true);

        String str = "[{\"userName\":\"陈翔宇\",\"age\":1},{\"userName\":\"陈翔宇2\"}]";
        List<User> users = JSON.parseArray(str, User.class);
        Map<String, User> collect = users.stream().collect(Collectors.toMap(User::getUserName, y -> y));

        for (Map.Entry<String,User> entry : collect.entrySet()){
            User value = entry.getValue();
            value.setEmail("1");
        }
        System.out.println(users);
    }


    static class A{
        String name ;
        Integer age;
        boolean IsDelete;

        A build(String name){
            this.name = name;
            return this;
        }
        A build(Integer age){
            this.age = age;
            return this;
        }
        A build(boolean IsDelete){
            this.IsDelete = IsDelete;
            return this;
        }

        public A() {
        }
    }
}
