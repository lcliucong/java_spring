package com.example.spring.constant;

public enum ErrorEnum {
    COMMON_ERROR(10000,"运行出现错误"),
    LOGIC_ERROR(10000,"代码逻辑错误"),


    USER_NOT_EXIST(10001,"用户不存在"),
    USER_PASSWORD_ERROR(10002,"密码错误"),
    TOKEN_NOT_EXIST(10003,"token不存在")
    ;


    private Integer code;
    private String message;

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
    ErrorEnum(Integer code,String message) {
        this.code=code;
        this.message=message;
    }
}
