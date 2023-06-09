package com.example.spring.controller;

import com.example.spring.constant.ErrorEnum;
import com.example.spring.exception.ErrorException;
import com.example.spring.filter.UserListFilter;
import com.example.spring.pojo.UserList;
import com.example.spring.service.business.JwtService;
import com.example.spring.service.impl.UserListServiceImpl;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "main")
public class Login {

    @Resource
    UserListServiceImpl userListServiceImpl;

    /**
     * login示例, 附带token返回  *****
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login (@RequestBody @Validated UserListFilter userListFilter) throws ErrorException {
        String phone = userListFilter.getPhone();
        String password = userListFilter.getPassword();
        UserList userInfo = userListServiceImpl.getCacheUserByPhone(phone);
        if (userInfo==null){
            throw new ErrorException(ErrorEnum.USER_NOT_EXIST.getCode(),
                    ErrorEnum.USER_NOT_EXIST.getMessage());
        }
//        可以给每个用户创建账号的时候生成一个专属的Salt进行加解密
//        String md5Password = DigestUtils.md5Hex(password +  userInfo.getSalt()).toUpperCase();
        String md5Password = DigestUtils.md5Hex(password+"10086lc").toUpperCase();
        String realPassword = userListServiceImpl.getRealPassword(phone);
        if (!StringUtils.equals(md5Password, realPassword)){
            throw new ErrorException(ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                    ErrorEnum.USER_PASSWORD_ERROR.getMessage());
        }
        //可以将JwtServic的必要参数写到配置文件.properties中
        JwtService.secret = "10086";
        JwtService.header = "Authoriztion";
        JwtService.expireTime = 100000000;
        String token = JwtService.createToken(userInfo.getPhone());
        Map<String,Object> map = new HashMap<>();
        map.put("user",userInfo.getPhone());
        map.put("token",token);
        return Result.success(map);
    }
}
