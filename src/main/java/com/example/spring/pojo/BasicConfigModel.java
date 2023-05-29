package com.example.spring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BasicConfigModel {

    private Integer id;

    private Integer subId;

    private String mark;

    private String markValue;

    private Date createTime;

    private Date updateTime;
}
