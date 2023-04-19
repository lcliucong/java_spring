package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.config.RabbitMQConfig;
import com.example.spring.config.RabbitSendEmailConfig;
import com.mysql.cj.xdevapi.JsonString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
         * 消息队列消费者
         */
@Service
@Slf4j
public class RabbitMqReceiverController {

    /*
     * 消息接收
     * 执行
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveDemo(String message){
        log.info("Received-queueName: {},  message: {}", RabbitMQConfig.QUEUE_NAME,message);
    }

    /*
     * 消息接收
     */
    @RabbitHandler
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME_API})
    public void receiverApi(String message){
        log.info("received-queueName: {}, message: {}", RabbitMQConfig.QUEUE_NAME_API, message);
    }

    /*
     * 消费第一个队列
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitSendEmailConfig.QUEUE_SEND_EMAIL_NAME)
    public void sendEmailReceiver(String message){
        String afterReplace = "";
        if (StringUtils.startsWithIgnoreCase(message,"队列3")){
            afterReplace = StringUtils.replace(message,"队列3","最后一个队列");
        }
        log.info("Success! first Queue-receiver: {}, {}", message,afterReplace);
    }

    /*
     * 消费第二个队列
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitSendEmailConfig.QUEUE_SEND_EMAIL_NAME_2)
    public void sendEmailReceiver2(String message){
        String afterReplace = "";
        if (StringUtils.startsWithIgnoreCase(message,"队列3")){
            afterReplace = StringUtils.replace(message,"队列3","队列2的队列3");
        }
        log.info("in Seconde QueueReceiver: {}, {}", message,afterReplace);
    }

    /*
     * 消费sql队列
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitSendEmailConfig.QUEUE_SEND_EMAIL_SQL)
    public void sendEmailSql(String jsonParam){
        JSONObject jsonSqlParam = JSONObject.parseObject(jsonParam);
        //数据库业务
        System.out.println("username-----------------------------"+jsonSqlParam.getString("username"));
        log.warn("another-queue-param: {}", jsonParam);
        log.info("now u is in sql");
    }
}
