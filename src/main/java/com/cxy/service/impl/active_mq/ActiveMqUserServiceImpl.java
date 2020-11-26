//package com.cxy.service.impl.active_mq;
//
//import com.cxy.entity.User;
//import com.cxy.service.avtive_mq.ActiveMqService;
//import com.cxy.service.avtive_mq.ActiveMqUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.stereotype.Service;
//
///**
// * @program: template_project_mybatis_plus
// * @description:
// * @author: 陈翔宇
// * @create_time: 2020-11-21 11:55:10
// */
//@Service
//public class ActiveMqUserServiceImpl implements ActiveMqUserService {
//
//    /**
//     * 注入由springboot 自动产生的 jmsTemplate
//     */
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    /**
//     * 自定义地址
//     */
//    private static final String MY_DESTINATION = "my-destination";
//
//    @Override
//    public void sendMsg(User user) {
//        System.out.println("发送消息User：【" + user + "】");
////        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
////        jmsTemplate.setMessageConverter((MessageConverter) converter);
//        jmsTemplate.convertAndSend(MY_DESTINATION, user);
//    }
//
//    @Override
//    @JmsListener(destination = MY_DESTINATION)
//    public void receiveMsg(User user) {
//        System.out.println("接收消息User：【" + user + "】");
//    }
//}
