package com.example.spring.controller.Others;

import com.example.spring.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


        /**
         * 消息队列消费者
         */
@Service
@Slf4j
public class RabbitMqReceiverController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiverController.class);

    /*
     * 消息接收
     * 执行
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_DEMO)
    public void receiveDemo(String message){
        log.info("Received-queueName: {},  message: {}", RabbitMQConfig.QUEUE_NAME_DEMO,message);
    }

    /*
     * 消息接收
     */
    @RabbitHandler
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME_API})
    public void receiverApi(String message){
        log.info("received-queueName: {}, message: {}", RabbitMQConfig.QUEUE_NAME_API, message);
    }
}
