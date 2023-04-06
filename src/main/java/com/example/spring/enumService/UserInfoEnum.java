package com.example.spring.enumService;

public enum UserInfoEnum {

    USER_INFO("USER_INFO");
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    UserInfoEnum(String message){
        this.message = message;
    }
}
