package com.example.spring.service.interfaces;

import com.example.spring.pojo.MemberModel;

import java.util.List;

public interface MemberService {

    List<MemberModel> selectAll();
}
