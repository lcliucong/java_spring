package com.example.spring.daos.second;

import com.example.spring.pojo.MemberModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper层，dao层     Dao层调用entity层(Model层)
 * 对数据库实现持久化操作，在此Dao层中增删改查功能，与xx.xml文件对应
 */
@Mapper //必须加上注解，不然找不到
public interface MemberModelMapper {
        List<MemberModel> selectAll();
}
