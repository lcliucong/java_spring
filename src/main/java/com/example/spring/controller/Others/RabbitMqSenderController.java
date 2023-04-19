package com.example.spring.controller.Others;

import com.example.spring.config.RabbitMQConfig;
import com.example.spring.config.RabbitSendEmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sender
 * rabbitTemplate的convertAndSend()方法可以给指定队列发送消息，
 * 函数有三个参数，
 * 第一个是**交换机(exchange)的名字,
 * 第二个是路由键(routing-key)**的名字，
 * 第三个则为消息的内容。
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
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, "hello----by rabbitMq" + message);
    }

    /*
     * 使用直连交换机向demo key发送消息
     * 交换机名称: {@link RabbitMQConfig # DIRECT_EXCHANGE_NAME_DEMO}
     * 生产者路由键: {@link RabbitMQConfig # QUEUE_SENDER_ROUTING_KEY_DEMO{
     */
    public void sendToDirectDemo(String message){
        log.error("exchange_name: {}, sender out key ={} message= {}",RabbitMQConfig.DIRECT_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO, message);
        log.info("this queue-name : {},  send message is : {}", RabbitMQConfig.QUEUE_NAME, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO, message);
    }

    /*
     * 使用主题交换机向demo key 发送消息
     * 交换机名称: {@link RabbitMQConfig # TOPIC_EXCHANGE_NAME_DEMO}
     * 生产者路由键: {@link RabbitMQConfig # QUEUE_SENDER_ROUTING_KEY_DEMO}
     */
    public void sendToTopicDemo(String message){
        log.error("exchange-name: {}, queue-sender-out-key: {}, message = {}",RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_DEMO, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_DEMO, message);
    }

    /*
     * 使用主题交换机向demo2 key发送消息
     */
    public void sendToTopicDemo2(String message){
        log.error("exchange_name:{} queue-sender-out-key: {}, message: {}", RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO_2, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_DEMO, RabbitMQConfig.QUEUE_SENDER_ROUTING_KEY_DEMO_2, message);
    }

    public void sendToTopicApiUser(String message){
        log.error(   "exchange_name:{} " +
                        "queue-sender-out-key: {}," +
                        " message: {}",
                RabbitMQConfig.TOPIC_EXCHANGE_NAME_API, //exchange_name
                RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_API, //queue-sender-out-key
                message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME_API,
                RabbitMQConfig.TOPIC_EXCHANGE_ROUT_KEY_API, message);
    }

    public void sendEmail(String message1, String message2, String message3){
        //交换机名称， 生产者路由键
        //调用了三个消费者  RabbitMqReceiverController
        rabbitTemplate.convertAndSend(RabbitSendEmailConfig.QUEUE_TOPIC_NAME, RabbitSendEmailConfig.QUEUE_SENDER_ROUTE_KEY, message1);
        rabbitTemplate.convertAndSend(RabbitSendEmailConfig.QUEUE_TOPIC_NAME, RabbitSendEmailConfig.QUEUE_SENDER_ROUTE_KEY_2, message2);
        rabbitTemplate.convertAndSend(RabbitSendEmailConfig.QUEUE_TOPIC_NAME, RabbitSendEmailConfig.QUEUE_SENDER_ROUTE_KEY_3, message3);
    }
    public void makeSql(String sqlParam){
        rabbitTemplate.convertAndSend(RabbitSendEmailConfig.QUEUE_TOPIC_NAME, RabbitSendEmailConfig.QUEUE_SENDER_ROUTE_KEY_SQL, sqlParam);
    }














}
