package com.cxy.controller.asycn;

import com.cxy.service.async.IAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: template_project_mybatis_plus
 * @description: 异步线程测试
 * @author: 陈翔宇
 * @create_time: 2020-11-21 10:14:54
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private IAsyncService asyncService;

    @RequestMapping("/generateReport")
    public String generateReport(){
        System.out.println("主线程名称:" + Thread.currentThread().getName());
        asyncService.generateReport();
        return "generateReport";
    }
}
