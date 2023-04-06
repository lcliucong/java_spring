package com.example.spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

@RestController
@Slf4j
public class Result {

    private int code = 10000;
    private String message = "操作成功";
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(Object data){
        Result res = new Result();
        res.setCode(res.code);
        res.setMessage(res.message);
        res.setData(data);
        return res;
    }
    public static Result success(){
        return new Result();
    }

}
