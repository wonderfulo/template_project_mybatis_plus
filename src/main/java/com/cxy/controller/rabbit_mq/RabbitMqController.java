//package com.cxy.controller.rabbit_mq;
//
//import com.cxy.common.JsonResponse;
//import com.cxy.entity.User;
//import com.cxy.service.rabbit_mq.RabbitMqService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @program: template_project_mybatis_plus
// * @description: RabbitMq控制类
// * @author: 陈翔宇
// * @create_time: 2020-11-21 15:08:15
// */
//@RestController
//@RequestMapping("/rabbit_mq")
//public class RabbitMqController {
//
//    @Autowired
//    private RabbitMqService rabbitMqService;
//
//    @RequestMapping("/sendMsg")
//    public JsonResponse<String> sendMsg(String message){
//        rabbitMqService.sendMsg(message);
//        return JsonResponse.success(message);
//    }
//
//
//    @RequestMapping("/sendUser")
//    public JsonResponse<User> sendUser(User user){
//        rabbitMqService.sendUser(user);
//        return JsonResponse.success(user);
//    }
//}
