package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/string")
public class StringController {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    StringBuilder stringBuilder;

    /**
     *  StringBuilder
     *  StringBuilder是一个线程不安全的字符串类型
     *  适用于单线程操作大量数据，且运行速度较快
     */
    @RequestMapping(value = "/stringBuilder",method = RequestMethod.POST)
    public Result makeStringBuffer(@RequestParam String username){
        //创建一个StringBuilder 字符串
        StringBuilder stringBuilder = new StringBuilder("this is a StringBuilder string");
        //向StringBuilder后追加字符串
        stringBuilder.append(",this is append string");
        //键补存在则新增
        Boolean bools = stringRedisTemplate.opsForValue().setIfAbsent("StringBuilder_redis", String.valueOf(stringBuilder),30, TimeUnit.SECONDS);
        if (!bools){
            //存在，则向键值后添加数据
            stringRedisTemplate.opsForValue().append("StringBuilder_redis",String.valueOf(stringBuilder));
        }
        return Result.success(stringBuilder);
    }
    @RequestMapping(value = "stringBuilderOper", method = RequestMethod.POST)
    public Result StringBuilderOperate (@RequestParam String username){
        stringBuilder = new StringBuilder("StringBuilder");
        // append向末尾添加数据
        stringBuilder.append(" string");
        //insert(int 开始位置, string 要增加的数据)从指定位置后开始，插入数据
        stringBuilder.insert(0,"this is ");
        stringBuilder.insert(14," ");
        //delete(int 开始位置, int 结束位置)删除开始位置到结束位置的字符
        stringBuilder.delete(0,8);
        //indexOf(string 要搜索的字符串, [int 从指定索引位置开始搜索])获取要搜索的字符串出现的索引位置
        int b  =stringBuilder.indexOf("B");
        int u = stringBuilder.indexOf("i",5);
        //replace(int 开始位置, int 结束位置, string 替换字符)
        stringBuilder.replace(7,14,"StringBuilder");
        //lastIndexOf(string 要搜索的字符串, [int 从指定索引位置开始搜索]) 获取要搜索的字符串最后一次出现的位置
        int last = stringBuilder.lastIndexOf("i");
        String[] s = new String[]{String.valueOf(last), String.valueOf(stringBuilder.length()),stringBuilder.toString()};
        return Result.success(s);
    }
    @RequestMapping(value = "stringBufferOper", method = RequestMethod.POST)
    public Result StringBufferOperte (@RequestParam String username){
        StringBuffer stringBuffer = new StringBuffer("StringBuffer");
        stringBuffer.insert(0,"this is ");
        return Result.success(stringBuffer);
    }





    @RequestMapping("stringBufferOther")
    public Result stringBufferOther(){
        StringBuffer stringBuffer = new StringBuffer("StringBuffer");
        stringBuffer.insert(0,"make ");
        stringBuffer.replace(5,stringBuffer.length() - stringBuffer.indexOf("StringBuffer")-1,"buffer");
        log.info("stringBuffer: {}", stringBuffer);
        stringBuffer.replace(stringBuffer.lastIndexOf("Buffer"),stringBuffer.length(),"String");
        log.info("last: {}", stringBuffer);

        return Result.success(stringBuffer);
    }
}
