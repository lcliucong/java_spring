package com.example.spring.service.interfaces;


import com.example.spring.pojo.BasicConfigModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasicConfigService {

    List<BasicConfigModel> selectAll();

    @Transactional(rollbackFor = Exception.class)
    int insert(BasicConfigModel condition);
}
