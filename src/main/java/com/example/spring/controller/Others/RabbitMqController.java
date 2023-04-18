package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RabbitMq 调用接口 调用此接口向Sender 发送队列
 */
@RestController
@Component
@Slf4j
public class RabbitMqController {

    @Resource
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitMqSenderController rabbitMqSenderController;


    //创建生产者 直连
    @RequestMapping(value = "mqService", method = RequestMethod.POST)
    public void produce(){
        rabbitTemplate.convertAndSend("testSendMsg", "project-name");
    }

    /*
     * 发送测试
     */
    @RequestMapping(value = "send")
    public Result send(){
        int count = 100;
        for (int i = 0; i < 100; i++){
            rabbitMqSenderController.send(String.valueOf(i));
        }
        return Result.success();
    }

    @RequestMapping(value = "send/exchange")
    public Result send2(){
        rabbitMqSenderController.sendToDirectDemo("send direct demo");
        rabbitMqSenderController.sendToTopicDemo("send topic demo");
        rabbitMqSenderController.sendToTopicDemo2("send topic demo2");
        rabbitMqSenderController.sendToTopicApiUser("send tipic api user");
        return Result.success();
    }










































































}