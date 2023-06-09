package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.pojo.BasicConfigModel;
import com.example.spring.service.impl.BasicConfigServiceImpl;
import com.example.spring.service.interfaces.BasicConfigService;
import com.example.spring.utils.Result;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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

    //多数据源查询
    @RequestMapping(value = "member")
    public Result getMember(){
        List<BasicConfigModel> list = basicConfigService.selectAll();
        return Result.success(list);
    }

    //多数据源事务操作
    @RequestMapping(value = "addConfig")
    @Transactional(transactionManager = "automaticTransactionManager")
    public Result addConfig(@RequestBody @Validated String jsonParam){
        JSONObject jsonParams = JSONObject.parseObject(jsonParam);
        String mark = jsonParams.getString("mark");
        String markValue = jsonParams.getString("markValue");
        Integer subId = jsonParams.getInteger("subId");
        BasicConfigModel basicConfigModel = new BasicConfigModel();
        basicConfigModel.setMark(mark);
        basicConfigModel.setMarkValue(markValue);
        basicConfigModel.setSubId(subId);
        int record = basicConfigService.insert(basicConfigModel);
        return Result.success();
    }














}
