package com.example.spring.controller.Others;

import com.example.spring.filter.UserListFilter;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/***
 *  List相关
 *  有序集合（存储元素和取出元素顺序一样）
 *  有索引下标，可以使用一些索引方法
 *  可以存储重复值

 *  ArrayList 和  LinkedList
 */
@RestController
@Slf4j
@Component
@RequestMapping(value = "list")
public class ListController {
    /**
     * 创建List并添加值
     * 1.List<T> list = new ArrayList<>(要设置的初始大小){{add(); add(); add()}}
     * 2.List<T> list = new ArrayList<>();    list.add(元素);  list.add(索引,元素)
     * 3.List<T> list = new ArrayList<>().of(元素1，元素2，元素3)
     */
    @RequestMapping(value = "maket")
    public <T> CommonRes makeT(@RequestParam Integer id, String name, Integer sex){

        List<Object> list = new ArrayList<>(3){{
            add(id);
            add(name);
            add(sex);
        }};
        list.add(3,Integer.toString(id+1999));
        list.add(4,"通过list.add添加");
        list.add("没有填写索引");
        log.info("list: {}",list);
        log.info("list[3]: {}", list.get(3));
        int i = 0 ;
        for (Object one : list){
            i++;
            log.info("list["+i+"] : {}", one);
        }
        List<Object> list1 = List.of(id,name,sex);
        list1.forEach(str->{
            log.warn("one: {}" ,str);
        });

        //addAll方法
        List<Object> addAll = new ArrayList<>(){{
            add(0,"索引0");
            add(1,"索引1");
        }};
        addAll.addAll(list1);
        //生成时赋值
        List<Object> creat = new ArrayList<>(list1);
        log.info(creat.toString());
        return CommonRes.create(addAll);

    }
    @RequestMapping(value = "getlist")
    public CommonRes makeList(@RequestParam Integer id, String username, String  password, int phone){
        List<Object> list = new ArrayList<>(){{
            add(0,id); add(1,username);
            add(2,password);add(3,phone);
        }};
        //for循环获取值
        for (int i=0;i< list.size();i++){
            log.info("list.["+i+"]: {}",list.get(i));
        }
        //foreach 循环获取值
        list.forEach(one->{
            log.info("list."+one);
        });
        //for增强
        for (Object one :list){
            log.info("list.one: {}",one);
        }
        return CommonRes.create(list);
    }

    @RequestMapping(value = "deletelist",method = RequestMethod.POST)
    public Result deletes(@RequestBody UserListFilter tester){
        //移除
        String param_tester = tester.getTester();
        List<Object> list = new ArrayList<>(){{
            add(0,"username");
            add(1,"password");
            add(2,"phone");
        }};
        log.info("before ：{}", list);
        if(!list.contains(tester.getTester())){
            list.add(tester.getTester());
        }else{
            list.remove(param_tester);
        }
        log.info("after: {}", list);
        return Result.success(list);
    }

    @RequestMapping(value = "update")
    public Result updates(@RequestParam String newusername){
        //查询是否包含上传的值
        List<Object> list = new ArrayList<>(){{
            add(0,"username");
            add(1,"password");
            add(2,"phone");
        }};
        log.info(list.toString());
        if (list.contains(newusername)){
            int idx = list.indexOf(newusername);
            list.set(idx,"新数据");
        }else{
            list.add(newusername);
        }

        log.info(list.toString());
        return Result.success(list);
    }
    /**
     * 迭代器
     */
    @RequestMapping(value = "iterator")
    public Result iterators(@RequestParam String username , @RequestParam String password,
                            @RequestParam String user_phone,@RequestParam String usersex){
        List<String> list = new ArrayList<>(){{
            add(username);
            add(password);
            add(user_phone);
            add(usersex);
        }};
        Iterator<String> iterator = list.iterator();
        log.info("before: {}",list);
        while (iterator.hasNext()){
            log.info("next: {}",iterator.next());
            if (iterator.next().equals("1")){
                iterator.remove();
            }
        }
        return Result.success(list);

    }
}
