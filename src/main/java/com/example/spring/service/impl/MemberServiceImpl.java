package com.example.spring.service.impl;

import com.example.spring.daos.second.MemberModelMapper;
import com.example.spring.pojo.MemberModel;
import com.example.spring.service.interfaces.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberModelMapper memberMapper;

    @Override
    public List<MemberModel> selectAll() {
        return memberMapper.selectAll();
    }
}
