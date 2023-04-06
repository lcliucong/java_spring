package com.example.spring.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.spring.daos.UserListMapper;
import com.example.spring.enumService.UserInfoEnum;
import com.example.spring.pojo.UserList;
import com.example.spring.service.interfaces.UserListService;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


//service  实现接口类  调用Dao层，用来返回Dao层的数据
@Service
public class UserListServiceImpl implements UserListService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    @Resource
    private UserListMapper userListMapper;

    @Override
    public List<UserList> selectAll(UserList condition){
          return userListMapper.selectAll(condition);
    }

    @Override
    public List<UserList> getAll() {
        return userListMapper.getAll();
    }


    @Override
    public UserList selectByUsername(String username) {
        return userListMapper.selectByUsername(username);
    }

    @Override
    public List<UserList> selectBy(UserList condition) {
        return userListMapper.selectBy(condition);
    }

    @Override
    public int updateStatusById(UserList userList) {
        return userListMapper.updateStatusById(userList);
    }

    @Override
    public UserList selectByPhone(String phone) {
        String userinfo = stringRedisTemplate.opsForValue().get(phone);
        if(userinfo != null) {
            //json反序列化,将Json格式数据转换为普通格式
            return JSONObject.parseObject(userinfo,UserList.class);
        }
        UserList userInfo = userListMapper.selectByPhone(phone);
        if (userInfo != null){
            //json序列化，将普通格式转换为Json格式
            stringRedisTemplate.opsForValue().set(userInfo.getPhone(), JSONObject.toJSONString(userInfo));
            return userInfo;
        }
        return userInfo;

    }
    @Override
    public String getRealPassword(String phone){
        return userListMapper.getRealPassword(phone);
    }


}
