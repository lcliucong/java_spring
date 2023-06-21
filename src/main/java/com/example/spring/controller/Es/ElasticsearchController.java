package com.example.spring.controller.Es;

import com.example.spring.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
@RestController
@RequestMapping(value = "elasticsearch")
public class ElasticsearchController {

    @RequestMapping(value = "esSelect")
    public Result esSelect(){

        return Result.success();
    }
}
