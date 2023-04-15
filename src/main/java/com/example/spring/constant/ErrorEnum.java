package com.example.spring.constant;

public enum ErrorEnum {
    COMMON_ERROR(10000,"运行错误"),
    LOGIC_ERROR(10000,"代码逻辑错误")
    ;


    private Integer code;
    private String errMessage;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
    ErrorEnum(Integer code,String message) {
        this.code=code;
        this.errMessage=message;
    }
}
