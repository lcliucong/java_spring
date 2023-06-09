package com.example.spring.service.impl;

import com.example.spring.daos.second.BasicConfigModelMapper;
import com.example.spring.pojo.BasicConfigModel;
import com.example.spring.service.interfaces.BasicConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicConfigServiceImpl implements BasicConfigService {

    @Resource
    BasicConfigModelMapper basicConfigModelMapper;

    @Override
    public List<BasicConfigModel> selectAll() {
        return basicConfigModelMapper.selectAll();
    }

    @Override
    public int insert(BasicConfigModel condition) {
        return basicConfigModelMapper.insert(condition);
    }
}
