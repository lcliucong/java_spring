package com.example.spring.controller.Myb;

import com.example.spring.filter.Filters;
import com.example.spring.filter.UserBankFilter;
import com.example.spring.pojo.BookModel;
import com.example.spring.pojo.UserBankModel;
import com.example.spring.pojo.UserList;
import com.example.spring.service.impl.UserBankServiceImpl;
import com.example.spring.service.impl.BookServiceImpl;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.PageUtils;
import com.example.spring.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@Component
@RequestMapping(value = "bank")
public class UserBankController {
    @Resource
    UserBankServiceImpl userBankServiceImpl;
    @Resource
    BookServiceImpl bookServiceImpl;

    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public CommonRes getAll(@RequestBody @Validated UserBankFilter userBankFilter){
        pageFormat(userBankFilter);
        List<UserBankModel> list = userBankServiceImpl.getAll();
        //将查询到的结果进行分页处理
        PageInfo<UserBankModel> resultMakePage = new PageInfo<>(list);
        //将分页进行更细化的处理
        PageUtils<UserBankModel> pageUtils = new PageUtils<>();
        //添加一些需要的页码数据到 hashMap中
        HashMap<String,Object> result = pageUtils.returnMake(resultMakePage);
//        Object ctime = result.get("createTime");
        return CommonRes.create(result);
    }
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public CommonRes insert(@RequestBody UserBankFilter userBankFilter) throws Exception {
        UserBankModel userBankModel = new UserBankModel();
        userBankModel.setUserName(userBankFilter.getUserName());
        userBankModel.setUserBanknumber(userBankFilter.getUserBanknumber());
        userBankModel.setUserPassword(userBankFilter.getUserPassword());
        userBankModel.setUserStatus(userBankFilter.getUserStatus());
        userBankModel.setUserBalance(userBankFilter.getUserBalance());
        userBankServiceImpl.saves(userBankModel);

//        BookModel bookModel = new BookModel();
//        bookModel.setBookName("测试12");
//        bookModel.setBookNumber("sf123446");
//        bookModel.setBookAmount(122);
//        bookServiceImpl.saves(bookModel);

        return CommonRes.create();
    }
    @RequestMapping(value = "selectByIdForUpdate", method = RequestMethod.POST)
//    @Transactional(rollbackFor = Exception.class)
    public Result selectByid(@RequestParam int id) throws Exception {
        List<UserBankModel> list = userBankServiceImpl.selectById(id);
        PageInfo<UserBankModel> makePage = new PageInfo<>(list);
        return Result.success(makePage);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public CommonRes update(@RequestBody UserBankFilter userBankFilter){
        UserBankModel userBankModel = new UserBankModel();
        userBankModel.setUserBalance(userBankFilter.getUserBalance());
        userBankModel.setId(userBankFilter.getId());
        int res = userBankServiceImpl.update(userBankModel);
        return CommonRes.create();
    }

    @RequestMapping(value = "updateName",method = RequestMethod.POST)
    public CommonRes updateBy(@RequestBody UserBankFilter userBankFilter){
        return CommonRes.create();
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam int ids){
        return Result.success(1);

    }
    @RequestMapping(value = "selectByTime", method = RequestMethod.POST)
    public Result selectByTime(@RequestBody UserBankFilter userBankFilter) throws ParseException {

        UserBankModel userBankModel = new UserBankModel();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = simpleDateFormat.parse(userBankFilter.getStartTime());

        String finalStartTime = simpleDateFormat.format(startTime);
        Date endTime = simpleDateFormat.parse(userBankFilter.getEndTime());
        String finalEndTime = simpleDateFormat.format(endTime);
        log.info("startTime: {}", finalStartTime);
        log.info("endTime: {}", finalEndTime);
        List<UserBankModel> list = userBankServiceImpl.selectByTime(finalStartTime,finalEndTime);
        return Result.success(list);
    }

    public void pageFormat(Filters filter){
        PageHelper.startPage(filter.getPage(), filter.getSize());
        PageHelper.orderBy(filter.getOrderBy());
    }
}
