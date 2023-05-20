package com.example.spring.exception;

public class ErrorException extends Exception {
    private int code ;

    private String message ;

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
    public ErrorException(Integer code, String message){
        super();
        setCode(code);
        setMessage(message);
    }

}
