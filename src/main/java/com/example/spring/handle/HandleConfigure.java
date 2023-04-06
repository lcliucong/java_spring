package com.example.spring.handle;

import com.example.spring.exception.FilterException;
import com.example.spring.utils.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ControllerAdvice    @Controller的增强版
 * 主要用于处理全局数据
 * 一般搭配@ExceptionHandler(全局异常处理）、@ModelAttribute(全局数据绑定)、@InitBinder(全局数据预处理)
 */
@RestControllerAdvice   // @RestController + ControllerAdvice 结合
@Slf4j
//@RestController //@Controller 和 @ResponseBody结合
public class HandleConfigure {

//    @ResponseBody
//    @ExceptionHandler(Exception.class)
    public Error ExceptionCatch(Exception e){
        String err_class = String.valueOf(e.getClass());

//        return Error.make(500,err_class);
        List<String> list = new ArrayList<>(){{
           add(0,"getMessage: " + e.getMessage()) ;
           add(1, "getCause: " +e.getCause());
           add(2, "getLocalizeMessage: "+  e.getLocalizedMessage());
           add(3, "getClass: "+ e.getClass());
           add(4, "getClass: "+ e.getClass());
        }};
        log.info("err_list: {} ", list);
        return Error.make(500,list);
    }
    @ExceptionHandler(FilterException.class)
//    @ResponseBody
    public Error FilterHandler() {

        return Error.make(500,"其他错误");
    }


    //@Vaildate 和 BindingResult 配套使用  验证类错误
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
    public Error MethodNotVaildException(MethodArgumentNotValidException e) {
        BindingResult errstr = e.getBindingResult();
        List<Object> list = new ArrayList<>();
        if (errstr.hasErrors()){    //有错误
            for (FieldError fields : errstr.getFieldErrors()) {
                String maps = fields.getDefaultMessage();
                list.add(maps);
            }
        }
        return Error.make(20000, list);
        //StringBuilder
//        StringBuilder err_stringBuilder = new StringBuilder();
//        for (FieldError err_field : errstr.getFieldErrors()){
//            err_stringBuilder.append(err_field.getDefaultMessage()).append(",");
//        }
//        log.info("StringBuilder_ERR: {}" , err_stringBuilder);
//        return Result.error(500,err_stringBuilder);
    }


}
