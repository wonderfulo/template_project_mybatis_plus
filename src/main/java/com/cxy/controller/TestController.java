package com.cxy.controller;

import com.cxy.assert_package.AssertEx;
import com.cxy.constant.exception.DisplayTaskEnumException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：陈翔宇
 * @date ：2020/11/26 17:35
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/success")
    public String success(){
        return "success";
    }

    @RequestMapping("")
    public String test(){
        AssertEx.isNotEmpty(0L, DisplayTaskEnumException.ID_NOT_EXIST);
        return "success";
    }



}
