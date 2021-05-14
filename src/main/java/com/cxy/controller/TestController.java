package com.cxy.controller;

import com.cxy.assert_package.AssertEx;
import com.cxy.constant.exception.DisplayTaskEnumException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger  = LoggerFactory.getLogger(TestController.class);

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
        logger.info("------------info--------------{}",123);
        logger.error("------------error--------------{}",123);
        logger.debug("------------debug--------------{}",123);
        logger.trace("------------trace--------------{}",123);
        logger.warn("------------warn--------------{}",123);
        AssertEx.isNotEmpty(0L, DisplayTaskEnumException.ID_NOT_EXIST);
        return "success";
    }



}
