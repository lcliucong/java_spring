package com.example.spring.enumService;

public enum UserInfoEnum {

    USER_INFO(1,"USER_INFO");
    private Integer code;
    private String message;

    public Integer getCode(){return code;}
    public void setCode(Integer code){this.code = code;}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    UserInfoEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
