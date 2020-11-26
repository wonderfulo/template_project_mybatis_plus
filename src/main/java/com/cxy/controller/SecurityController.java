package com.cxy.controller;

import com.cxy.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: template_project_mybatis_plus
 * @description: springSecurity测试
 * @author: 陈翔宇
 * @create_time: 2020-11-18 18:06:08
 */
@Api("spring权限控制测试")
@RestController
@RequestMapping("/person")
public class SecurityController {

    /**
     * 根据 id 查询用户
     *
     * @param id
     */
    @ApiOperation(value = "获取测试用户列表")
    @RequestMapping("/findById/{id}")
    public void findById(@PathVariable("id") Integer id) {
        System.out.println(id);
    }

    @ApiOperation(value = "查询所有用户")
    @RequestMapping("/lists")
    public void lists() {
        System.out.println("查询所有用户");
    }

    @ApiOperation(value = "添加用户")
    @RequestMapping("/add")
    public void add(User user) {
        System.out.println("添加用户");
    }

    @ApiOperation(value = "修改用户")
    @RequestMapping("/update")
    public void update(User user) {
        System.out.println("修改用户");
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping("/del/{id}")
    public void del(@PathVariable("id") Integer id) {
        System.out.println("删除用户");
    }
}
