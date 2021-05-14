package com.cxy.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.cxy.assert_package.AssertEx;
import com.cxy.common.JsonResponse;
import com.cxy.constant.exception.DisplayTaskEnumException;
import com.cxy.entity.User;
import com.cxy2.ChenXiangYu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

    @RequestMapping(value = "/postTest", method = RequestMethod.POST)
    public JsonResponse<User> postTest(String accessToken, User user){
        System.out.println(accessToken);
        System.out.println(user);
        return JsonResponse.success(user);
    }

    @RequestMapping("")
    public String debug(){
        logger.info("------------info--------------{}",123);
        logger.error("------------error--------------{}",123);
        logger.debug("------------debug--------------{}",123);
        logger.trace("------------trace--------------{}",123);
        logger.warn("------------warn--------------{}",123);
        AssertEx.isNotEmpty(0L, DisplayTaskEnumException.ID_NOT_EXIST);
        return "success";
    }

    /**
     * hutool:远程调用测试
     * @return
     */
    @RequestMapping("/httpGet")
    public String httpGet(){
        String content = HttpUtil.get("http://localhost:8091/template/test/hello?accessToken=123");
        return content;
    }

    @RequestMapping(value = "/httpPost" , method = RequestMethod.POST)
    public String httpPost(){
        String url = "http://localhost:8091/template/test/postTest?accessToken=123";

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", "陈翔宇");

        String result1 = HttpUtil.post(url, paramMap);
        JsonResponse jsonResponse = JSON.parseObject(result1, JsonResponse.class);
        System.out.println(jsonResponse.getData());
        return result1;
    }


    /**
     * 本地jar包测试
     * @return
     */
    @RequestMapping(value = "/jarTest" , method = RequestMethod.GET)
    public ChenXiangYu jarTest(){
        ChenXiangYu chenXiangYu = new ChenXiangYu();
        chenXiangYu.setName("陈翔宇");
        return chenXiangYu;
    }


}
