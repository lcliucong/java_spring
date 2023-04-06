package com.example.spring.controller.Others;

import com.example.spring.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
@RestController
@Slf4j
@RequestMapping(value = "objects")
public class ObjectsUtils {
    @RequestMapping(value = "obj")
    public Result obj(@RequestParam String obj){
        if (Objects.isNull(obj)){
            log.info("null");
        }
        if (Objects.nonNull(obj)){
            log.info("not null");
        }
        return Result.success();
    }
}
