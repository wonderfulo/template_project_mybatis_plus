//package com.cxy.rabbit_mq_receiver;
//
//import com.cxy.entity.User;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @program: template_project_mybatis_plus
// * @description: RabbitMq的消息接收
// * @author: 陈翔宇
// * @create_time: 2020-11-21 15:12:30
// */
//@Component
//public class RabbitMqMessageReceiver {
//
//    /**
//     * 定义监听字符串队列名称
//     *
//     * @param msg
//     */
//    @RabbitListener(queues = {"${rabbitmq.queue.msg}"})
//    public void receiveMsg(String msg) {
//        System.out.println("收到消息：" + msg);
//    }
//
//    /**
//     * 定义监听用户队列名称
//     */
//    @RabbitListener(queues = {"${rabbitmq.queue.user}"})
//    public void receiveUser(User user) {
//        System.out.println("收到用户消息：" + user);
//    }
//
//}
