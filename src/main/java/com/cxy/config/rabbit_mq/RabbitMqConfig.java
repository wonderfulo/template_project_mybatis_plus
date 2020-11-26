//package com.cxy.config.rabbit_mq;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * @program: template_project_mybatis_plus
// * @description:
// * @author: 陈翔宇
// * @create_time: 2020-11-21 14:54:35
// */
//@Component
//public class RabbitMqConfig {
//
//    /**
//     * 消息队列名称
//     */
//    @Value("${rabbitmq.queue.msg}")
//    private String msgQueueName;
//
//    /**
//     * 用户队列名称
//     */
//    @Value("${rabbitmq.queue.user}")
//    private String userQueueName;
//
//    @Bean
//    public Queue createQueueMsg(){
//        // 创建字符串消息队列，boolean值代表是否持久化消息
//        return new Queue(msgQueueName, true);
//    }
//
//    @Bean
//    public Queue createQueueUser(){
//        // 创建User消息队列，boolean值代表是否持久化消息
//        return new Queue(userQueueName, true);
//    }
//}
