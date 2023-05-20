package com.example.spring.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.spring.daos.UserListMapper;
import com.example.spring.constant.UserInfoEnum;
import com.example.spring.pojo.UserList;
import com.example.spring.service.business.RedisService;
import com.example.spring.service.interfaces.UserListService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
    UserListMapper userListMapper;

    @Resource
    RedisService redisService;

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

        return userListMapper.selectByPhone(phone);

    }
    @Override
    public String getRealPassword(String phone){
        return userListMapper.getRealPassword(phone);
    }

    @Override
    public void insertAll(UserList userList) {
        userListMapper.insertAll(userList);
    }

    @Override
    public UserList getCacheUserByPhone(String phone) {
        String userInfoStr = redisService.GetStringKey(UserInfoEnum.USER_INFO.getMessage()+ phone);
        if (StringUtils.isNotBlank(userInfoStr)){
            //JSONObject.parseObject  可以写第二个参数来指定返回类型
            return JSONObject.parseObject(userInfoStr, UserList.class);
        }
        UserList userInfo = selectByPhone(phone);
        if (userInfo != null) {
            redisService.SetStringKey(UserInfoEnum.USER_INFO.getMessage() + phone, userInfo, 0);
            return userInfo;
        }
        return null;
    }


}
