package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.utils.AESUtil;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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


    //创建生产者 直连模式  对应Consumer.java
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
        return Result.success();
    }

    @RequestMapping(value = "sendEmail")
    public Result sendEmail(){
        String msg1 = "队列1发出";
        String msg2 = "队列2发出";
        String msg3 = "队列3执行";
        //调用producer生产者
        rabbitMqSenderController.sendEmail(msg1, msg2, msg3);

        //调用第二个生产者
        Map<String,Object> sqlparam = new HashMap<>();
        sqlparam.put("username","Javaer");
        sqlparam.put("password", AESUtil.AESEncrypt("123456","key","EBC"));
        sqlparam.put("phone","15966668888");
        sqlparam.put("sex",6);
        sqlparam.put("status",9);
        rabbitMqSenderController.makeSql(JSONObject.toJSONString(sqlparam));
        return Result.success();
    }










































































}