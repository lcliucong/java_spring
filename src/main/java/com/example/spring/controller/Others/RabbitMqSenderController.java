package com.example.spring.controller.Others;

import com.example.spring.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sender
 */
@Slf4j
@Service
public class RabbitMqSenderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * 使用简单模式发送消息  direct 直连
     */
    public void send(String message){
        log.info("send By Rabbitmq: {}",message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME_DEMO, "hello----by rabbitMq" + message);
    }

    /*
     * 使用直连交换机向demo key发送消息
     * 交换机名称: {@link RabbitMQConfig # DIRECT_EXCHANGE_NAME_DEMO}
     * 生产者路由键: {@link RabbitMQConfig # QUEUE_SENDER_ROUTING_KEY_DEMO{
     */
    public void sendToDirectDemo(String message){
        log.info("exchange_name: {}, sender out key ={} message= {}",RabbitMQConfig.DIRECT_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO, message);
    }

    /*
     * 使用主题交换机向demo key 发送消息
     * 交换机名称: {@link RabbitMQConfig # TOPIC_EXCHANGE_NAME_DEMO}
     * 生产者路由键: {@link RabbitMQConfig # QUEUE_SENDER_ROUTING_KEY_DEMO}
     */
    public void sendToTopicDemo(String message){
        log.info("exchange-name: {}, queue-sender-out-key: {}, message = {}",RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_DEMO, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_DEMO, message);
    }

    /*
     * 使用主题交换机向demo2 key发送消息
     */
    public void sendToTopicDemo2(String message){
        log.info("exchange_name:{} queue-sender-out-key: {}, message: {}", RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO_2, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO_2, message);
    }

    public void sendToTopicApiUser(String message){
        log.info("exchange_name:{} queue-sender-out-key: {}, message: {}", RabbitMQConfig.TOPIC_EXCHANGE_NAME_API,
                RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_API, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_API,
                RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_API, message);
    }













}
