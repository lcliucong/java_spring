package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "sender")
public class SenderController {

    @Resource
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "poster")
    public Result getPoster(){
        System.out.println(httpServletRequest.getHttpServletMapping());
        log.info(httpServletRequest.getRequestURI());
        return Result.success();
    }

}
