//package com.cxy.controller.active_mq;
//
//import com.cxy.common.JsonResponse;
//import com.cxy.entity.User;
//import com.cxy.service.avtive_mq.ActiveMqService;
//import com.cxy.service.avtive_mq.ActiveMqUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @program: template_project_mybatis_plus
// * @description:
// * @author: 陈翔宇
// * @create_time: 2020-11-21 12:00:26
// */
//@RestController
//@RequestMapping("/active_mq")
//public class ActiveMqController {
//    @Autowired
//    private ActiveMqService activeMqService;
//
//    @Autowired
//    private ActiveMqUserService activeMqUserService;
//
//    /**
//     * 测试字符串的发送
//     * @param message
//     * @return
//     */
//    @RequestMapping("/sendMsg")
//    public JsonResponse<String> sendMsg(String message){
//        activeMqService.sendMsg(message);
//        return JsonResponse.success(message);
//    }
//
//    /**
//     * 测试对象的发送
//     * @param userName
//     * @param age
//     * @return
//     */
//    @RequestMapping("/sendUser")
//    public JsonResponse<User> sendUser(String userName , String age){
//        User user = new User();
//        user.setUserName(userName);
//        user.setAge(Integer.parseInt(age));
//
//        activeMqUserService.sendMsg(user);
//        return JsonResponse.success(user);
//    }
//}
