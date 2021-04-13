package com.cxy.lambda;

import com.cxy.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName OptionTest.java
 * @Description
 * @createTime 2021年04月01日 09:33:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class OptionTest {

    @Test
    public void test(){

        User u = null;

        Integer integer = Optional.ofNullable(u).map(User::getAge).orElse(7);
        Integer integer2 = Optional.ofNullable(u).map(User::getAge).orElseGet(() -> 7);
        System.out.println(integer);
        System.out.println(integer2);

        List<User> list = null;
        List<User> users = Optional.ofNullable(list).orElse(new ArrayList<>());
        List<User> users1 = Optional.ofNullable(list).orElseGet(ArrayList::new);
    }


    public String getDefaultValue(){  //远程方法调用
        System.out.println("我被调用了!");
        return "我是默认值";
    }

    public ArrayList<User> getDefaultValue2(){  //远程方法调用
        System.out.println("我被调用了!");
        return new ArrayList<>();
    }
    @Test
    public void testSupplier(){
        Optional<String> opt = Optional.of("前端数据");
        String x = opt.orElse( getDefaultValue() );
        System.out.println("---以上为orElse调用,以下为orElseGet调用---");
        String y = opt.orElseGet(this::getDefaultValue);


        Optional<ArrayList<User>> opt2 = Optional.ofNullable(new ArrayList<User>());
        ArrayList<User> users = opt2.orElse(getDefaultValue2());
    }

}
