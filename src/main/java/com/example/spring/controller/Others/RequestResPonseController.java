package com.example.spring.controller.Others;

import com.example.spring.filter.UserBankFilter;
import com.example.spring.filter.UserListFilter;
import com.example.spring.pojo.UserBankModel;
import com.example.spring.pojo.UserList;
import com.example.spring.service.impl.UserListServiceImpl;
import com.example.spring.utils.AESUtil;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("dec")
public class RequestResPonseController {

    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    HttpServletResponse httpServletResponse;
    @Resource
    UserListServiceImpl userListServiceImpl;
    @RequestMapping(value = "math", method = RequestMethod.POST)
    public Result math (@RequestBody UserBankFilter userBankFilter){
        UserBankModel userBankModel = new UserBankModel();
        HashMap<Object,Object> maps = new HashMap<>();
        userBankModel.setId(userBankFilter.getId());
        int status = userBankFilter.getUserStatus();
        log.info("status: {}",status);
        switch (status){
            case 0 -> userBankModel.setUserStatus(1000);
            case 1 -> userBankModel.setUserStatus(2000);
        }
        maps.put("new_stas",userBankModel.getUserStatus());
        //向上入 保留2位小数
        BigDecimal db = new BigDecimal("1.987").setScale(2, RoundingMode.HALF_UP);
        maps.put("balance", db);
        BigDecimal newb = db.add(BigDecimal.valueOf(1.2));
//        int result = userListServiceImpl.updateStatusById(userList);
        return Result.success(newb);
    }

    @RequestMapping(value = "makm", method = RequestMethod.POST)
    public Result makm(@RequestParam String mat){
        HashMap<Object,Object> maps = new HashMap<>();
        Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
        while (enumeration.hasMoreElements()){
            maps.put(enumeration.nextElement(),httpServletRequest.getHeader(enumeration.nextElement()));
        }
        httpServletResponse.setHeader("Content-Type","application/notjson");
        httpServletResponse.addHeader("added-header", String.valueOf(httpServletResponse.getStatus()));
        Cookie cok = new Cookie("set_cook","cook_name");
        cok.setMaxAge(30);
        cok.setPath(httpServletRequest.getRequestURI());
        cok.setHttpOnly(true);
        httpServletResponse.addCookie(cok);
        httpServletResponse.setStatus(500);
        return Result.success(maps);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result Tlogin(@RequestBody @Validated UserListFilter userListFilter) throws Throwable {
        String userName = userListFilter.getPhone();
        String password = userListFilter.getPassword();
        log.info("name: {}",userName);
        UserList exist = userListServiceImpl.selectByPhone(userName);
        if (exist == null){
            return Result.success("暂无信息");
        }
        log.info("pwd: {}", password);
        log.info("database_pwd: {}",AESUtil.AESEncrypt(password,"ECB","ECB"));
        String truePassword = userListServiceImpl.getRealPassword(userName);
        log.info("true-pwd: {}", truePassword);
        if (!AESUtil.AESEncrypt(password, "ECB", "ECB").equals(truePassword)){
            return Result.success("密码错误");
        }
        String md5x = DigestUtils.md5Hex(password);
        log.info("md5after: {}", md5x);
        return Result.success("登录成功");
    }
    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public Result regist(@RequestBody UserListFilter userListFilter){
        String userName = userListFilter.getUsername();
        String password = userListFilter.getPassword();
        return Result.success();
    }


}
