//package com.cxy.service.impl.rabbit_mq;
//
//import com.cxy.entity.User;
//import com.cxy.service.rabbit_mq.RabbitMqService;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
///**
// * @program: template_project_mybatis_plus
// * @description: RabbitMq服务接口
// * @author: 陈翔宇
// * @create_time: 2020-11-21 15:01:07
// */
//@Service
//public class RabbitMqServiceImpl implements RabbitTemplate.ConfirmCallback, RabbitMqService {
//
//    /**
//     * 消息队列名称
//     */
//    @Value("${rabbitmq.queue.msg}")
//    private String msgRouting;
//
//    /**
//     * 用户队列名称
//     */
//    @Value("${rabbitmq.queue.user}")
//    private String userRouting;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Override
//    public void sendMsg(String message) {
//        System.out.println("rabbit发送字符串消息：【" + message + "】");
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.convertAndSend(msgRouting,message);
//    }
//
//    @Override
//    public void sendUser(User user) {
//        System.out.println("rabbit发送用户消息：【" + user + "】");
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.convertAndSend(userRouting,user);
//    }
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean b, String s) {
//        if (b) {
//            System.out.println("消息成功消费");
//        } else {
//            System.out.println("消息消费失败：" + s);
//        }
//    }
//}
