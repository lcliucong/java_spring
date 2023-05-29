package com.example.spring.daos.second;

import com.example.spring.pojo.BasicConfigModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BasicConfigModelMapper {

    List<BasicConfigModel> selectAll();
}
