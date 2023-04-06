package com.example.spring.service.impl;

import com.example.spring.daos.UserBankMapper;
import com.example.spring.filter.UserBankFilter;
import com.example.spring.pojo.UserBankModel;
import com.example.spring.service.interfaces.UserBankService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserBankServiceImpl implements UserBankService {
    @Resource
    UserBankMapper userBankMapper;

    @Override
    public List<UserBankModel> getAll() {
        return userBankMapper.getAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saves(UserBankModel userBankModel) throws Exception {
                return userBankMapper.saves(userBankModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserBankModel> selectById(int id) throws Exception{
        return userBankMapper.selectById(id);
    }
    @Override
    public int updateById(UserBankModel userBankModel){
        return userBankMapper.updateById(userBankModel);
    }
    @Override
    public int update(UserBankModel userBankModel) {
        return userBankMapper.update(userBankModel);
    }

    @Override
    public List<UserBankModel> selectByTime(String startTime, String endTime) {
        return userBankMapper.selectByTime(startTime,endTime);
    }

    @Override
    public List<UserBankModel> selectBy(UserBankModel condition) {
        return userBankMapper.selectBy(condition);
    }

}
