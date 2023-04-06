package com.example.spring.controller.Myb;

import com.example.spring.filter.UserBankFilter;
import com.example.spring.filter.UserListFilter;
import com.example.spring.service.impl.UserBankServiceImpl;
import com.example.spring.service.interfaces.UserBankService;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import com.example.spring.pojo.UserBankModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RestController
@RequestMapping("bytime")
public class SelectByTime {
    @Resource
    UserBankServiceImpl userBankServiceImpl;
    @Resource
    UserBankService userBankService;
    @RequestMapping("times")
    public Result selectByTime(@RequestBody UserBankFilter userBankFilter) throws ParseException {
        UserBankModel userBankModel = new UserBankModel();
        userBankModel.setUserName(userBankFilter.getUserName());
        userBankModel.setUserBanknumber(userBankFilter.getUserBanknumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = userBankFilter.getStartTime();
        String endTime = userBankFilter.getEndTime();
        String startFor = simpleDateFormat.format(simpleDateFormat.parse(startTime));
        String endFor = simpleDateFormat.format(simpleDateFormat.parse(endTime));
        log.info("userName: {}", userBankFilter.getUserName());
        log.info("startTime: {}", startFor);
        List<UserBankModel> list = userBankServiceImpl.selectBy(userBankModel);
        return Result.success(list);
    }

    @RequestMapping("setStatus")
    public Result setStatus(@RequestBody UserBankFilter userBankFilter){
        UserBankModel userBankModel = new UserBankModel();
        userBankModel.setId(userBankFilter.getId());
        int userStatus = userBankFilter.getUserStatus();
        switch (userStatus){
            case 0 -> userBankModel.setUserStatus(10000);
            case 1 -> userBankModel.setUserStatus(20000);
        }
        int result = userBankServiceImpl.update(userBankModel);
        log.info(String.valueOf(result));
        return Result.success();
    }
}
