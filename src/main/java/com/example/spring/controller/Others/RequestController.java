package com.example.spring.controller.Others;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("request")
public class RequestController {

    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    HttpServletResponse httpServletResponse;

    @RequestMapping(value = "post")
    public void requests() {
        List<String> list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        String uri = httpServletRequest.getRequestURI();
        map.put("requestUri", uri);
        String method = httpServletRequest.getMethod();
        map.put("getMethod", method);
        //headers 集合
        Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            map.put(enumeration.nextElement(),httpServletRequest.getHeader(enumeration.nextElement()));
        }
        //getParameter获取请求路径param
        System.out.println(httpServletRequest.getHttpServletMapping());

        httpServletResponse.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.println(JSONObject.toJSONString(map));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("setResponse")
    public Result HttpResponse() throws IOException {
        List<String> list = new ArrayList<>();
        String remote = httpServletRequest.getRemoteAddr();
        String remote_host = httpServletRequest.getRemoteHost();
        Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
        HashMap<String , String> headerMaps= new HashMap<>();
        while (enumeration.hasMoreElements()){
            headerMaps.put(enumeration.nextElement(),httpServletRequest.getHeader(enumeration.nextElement()));
        }
        httpServletResponse.setDateHeader("expire",new Date().getTime());
        httpServletResponse.setHeader("pragma", "no-cache");
        httpServletResponse.addCookie(new Cookie("user_token_cookie", "cookie_value_usertoken"));
        httpServletResponse.setStatus(500);
//        httpServletResponse.sendRedirect("post");   //设置重定向，重定向地址为post路由方法
        return Result.success(headerMaps);
    }

}
