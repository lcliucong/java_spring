package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequestMapping(value = "set")
public class SetController {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping(value = "makeset")
    public Result makeset(@RequestParam String username){
        HashSet<Object> sets = new HashSet<>(){{
            add(username);
            add("password");
            add("phone");
            add("sex");
        }};
        log.info(String.valueOf(sets.size()));
        for (Object set : sets) {
            log.info(String.valueOf(set));
        }
        return Result.success(sets.size());
    }
    @RequestMapping(value = "createMap")
    public Result createSet(@RequestParam String username){
        HashMap<String,String> maps = new HashMap<>();
        maps.put("idx","test1");
        maps.put("two","test2");
        stringRedisTemplate.opsForHash().putAll("user_test",maps);
        List<String> list = new ArrayList<>(){{
            add("username");
            add("password");
            add("sex");
        }};
        stringRedisTemplate.opsForList().leftPushAll("user",list);
        return Result.success(System.currentTimeMillis());
    }

    /**
     * compute将键值更新
     */
    @RequestMapping(value = "getMap")
    public Result getset(@RequestParam String  username,@RequestParam String password,
                             @RequestParam String user_phone,@RequestParam String usersex){
        HashMap<String,Object> contents = new HashMap<>();
        contents.put("name",username);
        contents.put("password",password);
        contents.put("phone",user_phone);
        contents.put("sex",usersex);
        log.info("before: {}",contents);
        String afterPhone = (String) contents.compute("phone",(key, value)->"15933445253");
        log.info("after: {}",afterPhone);
        log.info("after_hashMap: {}",contents);
        return Result.success(contents);
    }

    /**
     * 存在则更新，不存在则新增
     */
    @RequestMapping(value = "megerMap")
    public Result merges(@RequestParam String  username,@RequestParam String password,
                         @RequestParam String user_phone,@RequestParam String usersex){
        HashMap<String,Object> contents = new HashMap<>();
        contents.put("name",username);
        contents.put("password",password);
        contents.put("phone",user_phone);
        contents.put("sex",usersex);
        log.info("before: {}", contents);
        contents.merge("sex_mean","男", (oldvalue, newvalue)->"1");
        log.info("after: {}",contents);
        return Result.success();
    }

    /**
     * key对应的值不存在就新增，存在返回value
     */
    @RequestMapping(value = "computeIfAbSent")
    public Result Compontes(@RequestParam String  username,@RequestParam String password,
                         @RequestParam String user_phone,@RequestParam String usersex){
        HashMap<String,Object> contents = new HashMap<>();
        contents.put("name",username);
        contents.put("password",password);
        contents.put("phone",user_phone);
        contents.put("sex",usersex);
        log.info("before: {}",contents);
        contents.computeIfAbsent("rephone", key->15933);
        log.info("after: {}",contents);
        for (Map.Entry<String,Object> entry : contents.entrySet()){
            log.info("entry: {}",entry);
        }
        return Result.success(contents.entrySet());
    }

    @RequestMapping(value = "hashMap")
    public Result hashMap (@RequestParam String username,@RequestParam String password, int id){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("is username",username);
        hashMap.put("is password",password);
        hashMap.put("is id ", String.valueOf(id));
        if (hashMap.containsValue("redis_user")){
            //遍历hashMap,  keyset()可以获取到key值
            for (String key : hashMap.keySet()){
                if (hashMap.get(key).equals("redis_user")){
                    hashMap.replace(key,hashMap.get(key),"redis_user_plus");
                }
            }
        }
        log.info(hashMap.get("is username"));
        return Result.success(hashMap);
    }

}
