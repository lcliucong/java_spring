package com.example.spring.controller.Others;

import com.example.spring.utils.CommonRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "redis")
public class RedisController {

    /**
     * 装配redis模板对象
     * StringRedisTemplate 是以可读形式存储数据
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *  redisTemplate 是使用序列化类操作数据。存入Redis后不是以可读形式存储的
     */
//    private RedisTemplate redisTemplate;

    /**
     * opsForValue.set(key, value, timeout, timeUnit) 新增一个字符串类型的值，key是键, value是值, timeout过期时间, TimeUnit时间格式
     * opsForValue.set(key, value, offset) 从指定位置开始覆盖值
     */
    @RequestMapping(value = "setRedis")
    public CommonRes redisTest(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("timeout") int timeout){
//        String obj = JSONObject.toJSONString(username);
//        String obj1 = JSONObject.toJSONString(password);
        //opsForValue.set(key, value)
        stringRedisTemplate.opsForValue().set(username,password,timeout, TimeUnit.SECONDS);
        log.info("jsobj :{}", username);
        return CommonRes.create();
    }

    /**
     * opsForValue.get(key)  获取key键对应的值
     */
    @RequestMapping(value = "getRedis", method = RequestMethod.GET)
    public CommonRes redisGet(@RequestParam String keys){
        String values = stringRedisTemplate.opsForValue().get(keys);
        return CommonRes.create(values);
    }

    /**
     * 根据key  向已有的redis  value增加字符串到末尾
     */
    @RequestMapping(value = "appendRedis", method = RequestMethod.GET)
    public CommonRes redisAppend(@RequestParam String keys,
                                 @RequestParam String values)
    {
        stringRedisTemplate.opsForValue().append(keys,values);
        return CommonRes.create();
    }

    /**
     * 获取原来key键对应的值，并重新赋值
     * 返回值：上次的key对应的Value值
     */
    @RequestMapping(value = "getAndSet", method = RequestMethod.GET)
    public CommonRes getAndSet(@RequestParam("keys") String keys,
                               @RequestParam("values") String values){
        String res =stringRedisTemplate.opsForValue().getAndSet(keys,values);
        return CommonRes.create(res);
    }

    /**
     *  批量添加数据
     */
    @RequestMapping(value = "opsForSetAdd", method = RequestMethod.POST)
    public CommonRes opsForSetAdd (@RequestParam (name = "keys", required = false)String keys,
                                  @RequestParam("values1") String values1,
                                  @RequestParam String values2,
                                  String values3){
        Long res = stringRedisTemplate.opsForSet().add(keys,values1,values2,values3);
        log.info("keys: {}, values1: {}, values2: {}, values3: {}",keys,values1,values2,values3);
        return CommonRes.create();
    }
    /**
     * 键不存在则新增，存在则不操作
     */
    @RequestMapping(value = "opsForValuesSetIfAbSent", method = RequestMethod.POST)
    public CommonRes setIfAbSent(@RequestParam HashMap<String,String> params){

        boolean setted = Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(params.get("keys"), params.get("values")));
        if(!setted){
           stringRedisTemplate.opsForValue().append(params.get("keys"),params.get("values"));
        }
        log.info("keys: {}",params.get("keys"));
        log.info("values: {}",params.get("values"));
        return CommonRes.create();

    }

    /**
     *  接收Map格式的参数
     */
    @RequestMapping(value = "getAllParam", method = RequestMethod.POST)
    public CommonRes opsForSetMem(@RequestHeader String Accept,
                                  @RequestHeader String Host,
                                  @RequestParam Map<String, String> params){
        log.info("header.Accept: {}",Accept);
        log.info("header.host: {}",Host);
        log.info("params: {}",params);
        log.info("params.get-params3: {}", params.get("params3"));
        log.info("params.get-params6: {}", params.get("params6"));
        HashMap<String, String> maps = new HashMap<>();
        maps.put("host",Host);
        maps.put("Accept",Accept);
        maps.put("params中的params1",params.get("params1"));
        maps.put("params中的params4",params.get("params4"));
        return CommonRes.create(maps);
    }

}
