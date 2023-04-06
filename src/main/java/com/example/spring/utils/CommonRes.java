package com.example.spring.utils;

public class CommonRes {
    private Integer code = 10000;
    private String message = "success";
    private Object data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public static CommonRes create(Object data) {
        CommonRes commonRes = new CommonRes();
        commonRes.setData(data);
        return commonRes;
    }

    public static CommonRes create() {
        return new CommonRes();
    }
}
