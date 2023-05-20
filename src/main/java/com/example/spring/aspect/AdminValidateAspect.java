package com.example.spring.aspect;

import com.example.spring.constant.ErrorEnum;
import com.example.spring.constant.UserInfoEnum;
import com.example.spring.exception.ErrorException;
import com.example.spring.pojo.UserList;
import com.example.spring.service.business.JwtService;
import com.example.spring.service.business.RedisService;
import com.example.spring.service.impl.UserListServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;



@Component
@Aspect
public class AdminValidateAspect {

    @Resource
    HttpServletRequest httpServletRequest;

    @Resource
    HttpServletResponse httpServletResponse;

    @Resource
    UserListServiceImpl userListServiceImpl;

    @Resource
    RedisService redisService;

    //环绕增强，切点方法执行前、后
    @Around("execution(* com.example.spring.controller..*.*(..))" +
            "execution(* com.example.spring.controller.*.*.*(..)) ))")
    public Object validates(ProceedingJoinPoint pjp) throws Throwable {
        String token = httpServletRequest.getHeader("token");
        if (token==null || StringUtils.isBlank(token)){
            return com.example.spring.utils.Error.AspectSendErrResponse(httpServletResponse,ErrorEnum.TOKEN_NOT_EXIST.getCode(),
                    ErrorEnum.TOKEN_NOT_EXIST.getMessage());
        }
        String deToken = JwtService.validateToken(token);

        UserList userInfo = userListServiceImpl.getCacheUserByPhone(deToken);
        if (userInfo==null){
            return com.example.spring.utils.Error.AspectSendErrResponse(httpServletResponse,ErrorEnum.USER_NOT_EXIST.getCode(),
                    ErrorEnum.USER_NOT_EXIST.getMessage());
        }
        redisService.SetStringKey(UserInfoEnum.USER_INFO.getMessage()+deToken, userInfo);

        return pjp.proceed();
    }
}
