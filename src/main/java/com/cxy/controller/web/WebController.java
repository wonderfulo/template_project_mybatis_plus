package com.cxy.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: template_project_mybatis_plus
 * @description:
 * @author: 陈翔宇
 * @create_time: 2020-11-17 20:30:44
 */
@RequestMapping("/web")
@Controller
public class WebController {
    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }
}
