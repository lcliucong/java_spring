package com.example.spring.utils;

public class ErrorMessage extends Exception {
    private int code ;

    private String errmessage ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrmessage() {
        return errmessage;
    }

    public void setErrmessage(String errmessage) {
        this.errmessage = errmessage;
    }
    public ErrorMessage (Integer code, String errmessage){
        super();
        setCode(code);
        setErrmessage(errmessage);
    }

}
