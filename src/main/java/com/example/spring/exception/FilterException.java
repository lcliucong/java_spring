package com.example.spring.exception;

public class FilterException extends Exception{

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FilterException (Integer code, String message){
        super();
        setCode(code);
        setMessage(message);
    }
}
