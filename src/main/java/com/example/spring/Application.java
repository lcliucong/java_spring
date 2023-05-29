package com.example.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.spring.daos")
public class Application {

    public static void main(String[] args) {
//        System.out.println("test");
        SpringApplication.run(Application.class, args);
    }

}
