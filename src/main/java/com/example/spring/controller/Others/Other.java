package com.example.spring.controller.Others;

import com.example.spring.utils.CommonRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Component
@PropertySource("classpath:others.properties")
@PropertySource("classpath:application.properties")
@RequestMapping(value = "others")
public class Other {
    /**
     *
     * 事务
     * 锁
     * 日志
     * AES加密
     * JWT __ token
     * 注解的使用
     * 了解 PageHelper.startPage(filter.getPage(), filter.getSize());
     * JsonFormat处理日期格式放在pojo中
     * mybatis事务操作
     */

    @Value("${server.port}")
    private String port;

    @Value("${others.username}")
    private String username;
    @Value("${others.userpassword}")
    private String userpassword;

    @Value("${others.ipconfig}")
    private String ipconfig;

    @RequestMapping(value = "getconfig")
    public CommonRes getConfig(){
        return CommonRes.create(this.ipconfig);
    }
}
