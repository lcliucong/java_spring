package com.example.spring.service.business;

import com.example.spring.utils.RedisUtils;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisService {

    @Resource
    RedisUtils redisUtils;

    /**
     * 设置String类型Redis
     */
    public void SetStringKey(String cacheName, Object cacheValue, Integer expireTime){
        redisUtils.SetStringKey(cacheName, cacheValue, expireTime);
    }
    public void SetStringKey(String cacheName, Object cacheValue){
        redisUtils.SetStringKey(cacheName, cacheValue);
    }
    public String GetStringKey(String cacheName){
        return redisUtils.GetStringKey(cacheName);
    }

}
