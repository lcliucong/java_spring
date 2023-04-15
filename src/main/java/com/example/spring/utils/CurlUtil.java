package com.example.spring.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CurlUtil {
    static RestTemplate restTemplate = new RestTemplate();

    //post  携带headers
    public static ResponseEntity<String> post(String uri,Map<Object, String> ReqBody, Map<String, String> headers){
        ReqBody.put("test-value","tester_001");
        HttpHeaders httpHeaders = makeHeader();
        httpHeaders.set("user-token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7InVzZXJuYW1lIjoxNTYzMDE2NjgwMiwiaWQiOjEyODg3LCJpbnZpdGVfY29kZSI6IjgzNUEzMSIsImlzX3BhcnRuZXIiOjAsInBhcnRuZXIiOjY4LCJzdXBlcnZpc29yIjo2OCwiZ29vZ2xlX2F1dGgiOmZhbHNlfSwiaXNzIjoiT3JpZ2luYWxXaXNoZXMiLCJpYXQiOjE2ODA3NDIzNDUsImV4cCI6MTY4MDgyODc0NX0.UarLH0364zdoDnw2HUOICvY278wZfUnFnoL8tmgyLLM");
        for (String head : headers.keySet()){
            httpHeaders.set(head,headers.get(head));
        }
        //一个函数式接口，根据uri和HttpMethod 创建ClientHttpRequest发送请求
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //设置链接超时时间
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(30000);
        restTemplate.setRequestFactory(requestFactory);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(ReqBody),httpHeaders);
        return restTemplate.postForEntity(uri,httpEntity,String.class);
    }
    //post 无headers
    public static ResponseEntity<String> post(String uri, Map<?, ?> ReqBody){
        HttpHeaders httpHeaders = makeHeader();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(6000);
        requestFactory.setReadTimeout(30000);
        restTemplate.setRequestFactory(requestFactory);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(ReqBody), httpHeaders);
        return restTemplate.postForEntity(uri, httpEntity, String.class);
    }

    public static ResponseEntity<String> get(String uri, Map<String, String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String key : headers.keySet()){
            httpHeaders.set(key, headers.get(key));
        }
        log.info("headers: {} ", httpHeaders);
        httpHeaders.set("user-token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7InVzZXJuYW1lIjoxNTA1Nzg1OTY5MywiaWQiOjY3MTcsImludml0ZV9jb2RlIjoiRDJFNzJBIiwiaXNfcGFydG5lciI6MCwicGFydG5lciI6MjAxNywic3VwZXJ2aXNvciI6MjAxNywiZ29vZ2xlX2F1dGgiOmZhbHNlfSwiaXNzIjoiT3JpZ2luYWxXaXNoZXMiLCJpYXQiOjE2ODA0ODI4NjAsImV4cCI6MTY4MDU2OTI2MH0.6Cz8PvvYq42jUbH8A7U3L-zsPjRQ8qpHxAI3JVVelew");
        httpHeaders.set("content-type","application/json");
        httpHeaders.set("Connection","keep-alive");
        return restTemplate.getForEntity(uri,String.class,httpHeaders);
    }
    public static ResponseEntity<String> get(Map<String, String> params, String uri){
        //get 传递参数的两种方式
//        第一种
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost/index1?name={1}&parma2={2}", String.class, "张三","李四");
//        log.info("responseEntity: {} ", responseEntity);
//        第二种
//        ResponseEntity<String> responseEntity1 = restTemplate.getForEntity("http://localhost/index1?name={1}&parma2={2}", String.class, params);
//        第三种 将参数拼到uri里
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7InVzZXJuYW1lIjoxNTA1Nzg1OTY5MywiaWQiOjY3MTcsImludml0ZV9jb2RlIjoiRDJFNzJBIiwiaXNfcGFydG5lciI6MCwicGFydG5lciI6MjAxNywic3VwZXJ2aXNvciI6MjAxNywiZ29vZ2xlX2F1dGgiOmZhbHNlfSwiaXNzIjoiT3JpZ2luYWxXaXNoZXMiLCJpYXQiOjE2ODA0ODI4NjAsImV4cCI6MTY4MDU2OTI2MH0.6Cz8PvvYq42jUbH8A7U3L-zsPjRQ8qpHxAI3JVVelew");
        httpHeaders.set("content-type","application/json");
        httpHeaders.set("Connection","keep-alive");

        return restTemplate.getForEntity(uri,String.class,httpHeaders,params);
    }
    public static ResponseEntity<String> get(String uri){
        return restTemplate.getForEntity(uri,String.class);
    }
    private static HttpHeaders makeHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type","application/json");
        httpHeaders.add("connection","keepalive");
        return httpHeaders;
    }

    public static ResponseEntity<String> post2(String uri, Map<String, Object> reqBody, Map<String, String> headers) {
        HttpHeaders httpHeaders = DefaultHeaders();
        for (String key : headers.keySet()) {
            httpHeaders.add(key, headers.get(key));
        }
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(30000);
        restTemplate.setRequestFactory(requestFactory);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(reqBody), httpHeaders);
        return restTemplate.postForEntity(uri, httpEntity, String.class);
    }

    public static ResponseEntity<String> post1(String uri, Map<?, ?> reqBody) {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(30000);
        restTemplate.setRequestFactory(requestFactory);
        HttpHeaders httpHeaders = DefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(reqBody), httpHeaders);
        return restTemplate.postForEntity(uri, httpEntity, String.class);
    }

    private static HttpHeaders DefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Connection", "keep-alive");
        return httpHeaders;
    }
}
