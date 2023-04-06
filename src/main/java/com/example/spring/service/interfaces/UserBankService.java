package com.example.spring.service.interfaces;

import com.example.spring.filter.UserBankFilter;
import com.example.spring.pojo.UserBankModel;

import java.util.Date;
import java.util.List;

public interface UserBankService {

    List<UserBankModel> getAll();
    int saves(UserBankModel userBankModel) throws Exception;
    List<UserBankModel> selectById(int id) throws Exception;

    int updateById(UserBankModel userBankModel);

    int update(UserBankModel userBankModel);

    List<UserBankModel> selectByTime(String startTime, String endTime);

    List<UserBankModel> selectBy(UserBankModel condition);

}
