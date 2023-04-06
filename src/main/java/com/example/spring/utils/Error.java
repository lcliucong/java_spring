package com.example.spring.utils;

public class Error {
    private int code = 20000;

    private Object errmessage = "运行错误";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getErrmessage() {
        return errmessage;
    }

    public void setErrmessage(Object errmessage) {
        this.errmessage = errmessage;
    }
    public static Error make(Integer err_codes, Object err_messages){
        Error error = new Error();
        error.setCode(err_codes);
        error.setErrmessage(err_messages);
        return error;
    }

}
