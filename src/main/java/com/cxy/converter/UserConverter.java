package com.cxy.converter;

import com.cxy.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @program: template_project_mybatis_plus
 * @description: 用户参数转换器
 * @author: 陈翔宇
 * @create_time: 2020-11-14 16:08:04
 */
@Component
public class UserConverter implements Converter<String, User> {

    @Override
    public User convert(String s) {
        String[] userStr = s.split("-");
        User user = new User();
        user.setUserName(userStr[0]);
        user.setAge(Integer.parseInt(userStr[1]));
        user.setEmail(userStr[2]);
        return user;
    }
}