package com.example.spring.utils;


import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void SetStringKey(String cacheName, Object cacheValue, Integer expireTime){
        String JsonValue = JSONObject.toJSONString(cacheValue);
        stringRedisTemplate.opsForValue().set(cacheName,JsonValue,expireTime);
    }
    public void SetStringKey(String cacheName, Object cacheValue){
        String JsonValue = JSONObject.toJSONString(cacheValue);
        stringRedisTemplate.opsForValue().set(cacheName,JsonValue);
    }

    public String GetStringKey(String cacheName){
        return stringRedisTemplate.opsForValue().get(cacheName);
    }

}
