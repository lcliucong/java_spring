package com.example.spring.daos;

import com.example.spring.pojo.UserBankModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserBankMapper {

    List<UserBankModel> getAll();

    int saves(UserBankModel userBankModel);

    List<UserBankModel> selectById(int id);
    int update(UserBankModel userBankModel);

    int updateById(UserBankModel userBankModel);

    List<UserBankModel> selectByTime(String startTime, String endTime);

    List<UserBankModel> selectBy(UserBankModel condition);
}
