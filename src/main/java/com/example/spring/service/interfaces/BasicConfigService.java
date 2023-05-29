package com.example.spring.service.interfaces;


import com.example.spring.pojo.BasicConfigModel;

import java.util.List;

public interface BasicConfigService {

    List<BasicConfigModel> selectAll();
}
