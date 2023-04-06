package com.example.spring.controller.Myb;

import com.alibaba.fastjson2.JSON;
import com.example.spring.exception.FilterException;
import com.example.spring.filter.Filters;
import com.example.spring.pojo.UserList;
import com.example.spring.filter.UserListFilter;
import com.example.spring.service.impl.UserListServiceImpl;
import com.example.spring.service.interfaces.UserListService;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.PageUtils;
import com.example.spring.utils.Result;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@Component
@Slf4j
@RequestMapping(value = "getUser")
public class GetUserList {
    //业务层Service   Service层调用Dao接口
    @Resource
    UserListService userListService;
    @Resource
    @Autowired
    UserListServiceImpl userListServiceImpl;
    private PageHelperProperties pageHelperProperties;
//    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    //查询全部数据selectAll
    @RequestMapping(value = "getAllBy" , method = RequestMethod.POST)
    public CommonRes getAllBy(@RequestBody @Validated UserListFilter userListFilter){
//        formatPageHelper(userListFilter);
        UserList userList = new UserList();
        userList.setSex(userListFilter.getSex());
        userList.setUsername(userListFilter.getUsername());
        userList.setPhone(userListFilter.getPhone());
        List<UserList> list = userListServiceImpl.selectAll(userList);
//        PageInfo<UserList> pageInfo = new PageInfo<>(list);
        return CommonRes.create(list);
//        return userList;
    }
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public CommonRes getAll(@RequestBody @Validated UserListFilter userListFilter){
//        formatPageHelper(userListFilter);
        List<UserList> list =  userListServiceImpl.getAll();
        PageInfo<UserList> pageInfo = new PageInfo<>(list);
        PageUtils<UserList> pageUtils = new PageUtils<>();
        HashMap<String,Object> result = pageUtils.returnMake(pageInfo);
        return CommonRes.create(result);
    }
    //根据username查询
    @RequestMapping(value = "userlist", method= RequestMethod.POST)
    public CommonRes getUserListByName(@RequestBody UserListFilter username){
        UserList list = userListServiceImpl.selectByUsername(username.getUsername());
        return CommonRes.create(list);
    }
    @RequestMapping(value = "selectBy",method=RequestMethod.POST)
    public CommonRes selectBy(@RequestBody UserListFilter userListFilter){
        UserList userList = new UserList();
        userList.setSex(userListFilter.getSex());
        userList.setUsername(userListFilter.getUsername());
        userList.setPhone(userListFilter.getPhone());
        log.info("{}",userList.getUsername());
        log.info("{}",userList.getSex());
        log.info("{}",userList.getPhone());
        List<UserList> list = userListServiceImpl.selectBy(userList);
        return CommonRes.create(list);
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login (@RequestBody @Validated UserListFilter userListFilter) throws FilterException{
        UserList userList = new UserList();
        log.error("userListFilter: ->> {}" , userListFilter);
        userList.setUsername(userListFilter.getUsername());
        log.info(userListFilter.getUsername());

        return Result.success(1);
    }
    @RequestMapping(value = "makeE", method = RequestMethod.POST)
    public <E> Result makeE (@RequestParam(value = "username") E username, E password){
        Set<E> sets = new HashSet<>();
        sets.add(username);
        sets.add(password);
        log.info(sets.toString());
        Collection<E> collection = new ArrayList<>();
        return Result.success(sets.toArray());
    }
    @RequestMapping(value = "field",method = RequestMethod.POST)
    public <T> Result fields(@RequestParam(value = "username") T username,@RequestParam(value = "phone") T phone,
                             @RequestParam(value = "sex") T sex,@RequestParam(name = "age", required = false) T age){
        List<T> list = new ArrayList<T>(){{
            add(0, username);
            add(1, phone);
            add(2, sex);
            add(3, age);
        }};
        log.info(String.valueOf(list));
        return Result.success(list);
    }
    public void formatPageHelper(Filters filters){
        PageHelper.startPage(filters.getPage(), filters.getSize());
        PageHelper.orderBy(filters.getOrderBy());
    }

}
