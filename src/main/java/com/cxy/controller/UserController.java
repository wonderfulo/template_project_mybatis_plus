package com.cxy.controller;


import com.cxy.common.JsonResponse;
import com.cxy.entity.User;
import com.cxy.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-11-01
 */
@Api("用户信息处理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/testSelect")
    @ApiOperation(
            value = "获取测试用户列表",
            notes = "创建时间倒序",
            response = User.class,
            responseContainer = "List")
    public JsonResponse<List<User>> testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        return JsonResponse.success(userList);
    }


    @GetMapping(value = "/testConverter",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "测试转换器",
            notes = "测试转换器",
            response = User.class,
            responseContainer = "List")
    public JsonResponse<List<User>> testConverter(User user) {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        return JsonResponse.success(userList);
    }

    @GetMapping("/testConverter2")
    @ApiOperation(
            value = "测试转换器",
            notes = "测试转换器",
            response = User.class,
            responseContainer = "List")
    public JsonResponse<List<User>> testConverter2(User user) {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        return JsonResponse.success(userList);
    }

    @GetMapping("/testPath")
    @ApiOperation(
            value = "测试转换器",
            notes = "测试转换器",
            response = User.class,
            responseContainer = "List")
    public JsonResponse<List<User>> testPath(User user, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String realPath = request.getSession().getServletContext().getRealPath("");
        return null;
    }
}
