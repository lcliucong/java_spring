package com.example.spring.handle;

import com.example.spring.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import com.example.spring.utils.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.mybatis.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author ROCKY
 */
@Slf4j
@RestControllerAdvice
public class OverAllException {

    /**
     * 全局主动抛出异常
     */
    @ExceptionHandler(ErrorException.class)
    public Error ErrorExceptionHandler(ErrorException ex){
        return Error.make(ex.getCode(), ex.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Error handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return Error.make(50001, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Error handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                     HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return Error.make(50002, e.getMessage());
    }
    /**
     * 请求数据类型错误(请求体类型)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Error httpMessageNotReadAbleException(HttpMessageNotReadableException e){
        String err_msg = e.getMessage();
        log.info("请求体格式错误，错误类： {}",e.getClass());
        return Error.make(50003,"请求体格式错误");
    }
    /**
     * 请求地址不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Error httpRequestAddressNotFound(NoHandlerFoundException e,
                                            HttpServletRequest request){
        String err_msg = e.getMessage();
        String request_address = request.getRequestURI();
        log.info("requestUri: {}",request_address);
        log.info("requestPathInfo: {}", request.getPathInfo());
        log.info("requestMethod: {}", request.getMethod());
        log.info("request-header: {}", request.getHeader("Content-type"));
        log.info("请求地址: {} 不存在",request_address);
        return Error.make(404,err_msg);
    }

    /**
     * 业务异常
     */
//        @ExceptionHandler(ServiceException.class)
//        public Error handleServiceException(ServiceException e, HttpServletRequest request) {
//            log.error(e.getMessage(), e);
//            Integer code = e.getCode();
//            return StringUtils.isNotNull(code) ?
//                    Error.make(code, e.getMessage()) : Error.make(e.getMessage());
//        }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Error handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return Error.make(50004, e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult errstr = e.getBindingResult();
        StringBuilder errStringBuilder = new StringBuilder();
        List<FieldError> list = errstr.getFieldErrors();
        for (FieldError errs : list) {
            errStringBuilder.append(errs.getDefaultMessage()).append(",");
        }
        return Error.make(50006, errStringBuilder);
    }
    /**
     * 参数验证异常
     * 请求参数错误
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Error missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                         HttpServletRequest request){
        String err_msg = e.getMessage();
        Map<String, String[]> params = request.getParameterMap();
        log.info("请求地址: {}，请求参数错误",request.getRequestURI());
        return Error.make(50007,err_msg);
    }
    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Error handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return Error.make(50005, e.getMessage());
    }
}
