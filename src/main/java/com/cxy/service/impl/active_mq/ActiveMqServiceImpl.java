//package com.cxy.service.impl.active_mq;
//
//import com.cxy.service.avtive_mq.ActiveMqService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
///**
// * @program: template_project_mybatis_plus
// * @description:
// * @author: 陈翔宇
// * @create_time: 2020-11-21 11:55:10
// */
//@Service
//public class ActiveMqServiceImpl implements ActiveMqService {
//
//    /**
//     * 注入由springboot 自动产生的 jmsTemplate
//     */
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    @Override
//    public void sendMsg(String message) {
//        System.out.println("发送消息：【" + message + "】");
//        jmsTemplate.convertAndSend(message);
//        // 也可以自定义发送地址
//        // jmsTemplate.convertAndSend("your-destination",message);
//    }
//
//    @Override
//    @JmsListener(destination = "${spring.jms.template.default-destination}")
//    public void receiveMsg(String message) {
//        System.out.println("接收消息：【" + message + "】");
//    }
//
//    /**
//     * 可以多用户接收消息
//     *
//     * @param message
//     */
//    @JmsListener(destination = "${spring.jms.template.default-destination}")
//    public void receiveMsg2(String message) {
//        System.out.println("接收消息2：【" + message + "】");
//    }
//}
