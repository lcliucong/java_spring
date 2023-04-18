package com.example.spring.controller.Others;

import com.example.spring.pojo.UserList;
import com.example.spring.service.impl.UserListServiceImpl;
import com.example.spring.utils.AESUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;


@Component
public class Consumer {

    @Resource
    UserListServiceImpl userListServiceImpl;
    //消费
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("testSendMsg"))
    public void process(String productName) throws InterruptedException {
        int ints = 1;
        UserList userList = new UserList();
        userList.setUsername("xiaomingKing");
        userList.setPassword(AESUtil.AESEncrypt("123456","key","CBC"));
        userList.setPhone("123456787");
        userList.setSex(3);
        userList.setStatus(99);
        while (ints<9999){
            userListServiceImpl.insertAll(userList);
            ints++;
            sleep(10000);
            System.out.println("此次循环为第 "+ints+" 次 增加数据");
        }
    }
}
