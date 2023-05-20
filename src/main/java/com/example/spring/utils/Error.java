package com.example.spring.utils;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

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

    public static Object AspectSendErrResponse(HttpServletResponse httpServletResponse,
                                               Integer errCode, String errMsg) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf8");
        httpServletResponse.setCharacterEncoding("utf8");
        Writer writer = httpServletResponse.getWriter();
        Error err = Error.make(errCode, errMsg);
        writer.write(JSON.toJSONString(err));
        writer.flush();
        writer.close();
        return null;
    }


}
