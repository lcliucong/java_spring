package com.example.spring.controller.Others;

import com.example.spring.utils.AESUtil;
import com.example.spring.utils.CommonRes;
import com.example.spring.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Component
@Slf4j
@RequestMapping(value = "request")
public class GetRequest {

    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    HttpServletResponse httpServletResponse;
    @RequestMapping(value = "gethttp", method = RequestMethod.GET)
    public Object getHttpServ() throws IOException {
        String headers = httpServletRequest.getHeader("user-token");
        String requestUri = httpServletRequest.getRequestURI();
        log.info("requestUri: {}", requestUri);

        if (headers == null) {
            this.getHttpHearder();
        }
        return Result.success("成功");
    }

    /**
     * 设置响应头
     */
    public void getHttpHearder(){
       httpServletResponse.setContentType("application/json;charset=utf-8");
       httpServletResponse.setHeader("test","test_value");
    }
    /**
     * AES
     */
    @RequestMapping(value = "aes")
    public <T> CommonRes getAes(@RequestParam("str") String str){
        //aes加密
        String toaes = AESUtil.AESEncrypt(str,"keys","ECB");
        //aes解密
        String deaes = AESUtil.AESDecrypt(toaes,"keys","ECB");
        //md5加密
        String md5 = AESUtil.getMD5(str);
        List<String> list = new ArrayList<>();
        list.add(toaes);
        list.add(deaes);
        list.add(md5);
//        String[] res = {toaes,deaes,md5};

        return CommonRes.create(list);
    }

}
