package com.example.spring.controller.Others;

import com.example.spring.pojo.BasicConfigModel;
import com.example.spring.service.impl.BasicConfigServiceImpl;
import com.example.spring.service.interfaces.BasicConfigService;
import com.example.spring.utils.Result;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "sender")
public class SenderController {

    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    BasicConfigService basicConfigService;

    @RequestMapping(value = "poster")
    public Result getPoster() throws InterruptedException {
        log.info("ip:{},线程:{}", httpServletRequest.getRemoteAddr(), Thread.currentThread().getName());
//        log.info(httpServletRequest.getServletPath());
        Thread.sleep(500);
        return Result.success();
//        System.out.println(httpServletRequest.getHttpServletMapping());
//        log.info(httpServletRequest.getRequestURI());
//        return Result.success();
    }

    @RequestMapping(value = "member")
    public Result getMember(){
        List<BasicConfigModel> list = basicConfigService.selectAll();
        return Result.success(list);
    }

}
