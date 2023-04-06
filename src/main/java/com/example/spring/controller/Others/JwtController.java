package com.example.spring.controller.Others;

import com.example.spring.utils.CommonRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
@RestController
@RequestMapping(value = "jwt")
public class JwtController {

    @RequestMapping(value = "makeJwt" )
    public CommonRes makeJwt(){


        return CommonRes.create();
    }
}
