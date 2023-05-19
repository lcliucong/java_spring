package com.example.spring.pojo;

import com.example.spring.pojo.ZfbSign;
import com.example.spring.pojo.ZfbSignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZfbSignMapper {
    long countByExample(ZfbSignExample example);

    int deleteByExample(ZfbSignExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(ZfbSign record);

    int insertSelective(ZfbSign record);

    List<ZfbSign> selectByExampleWithBLOBs(ZfbSignExample example);

    List<ZfbSign> selectByExample(ZfbSignExample example);

    ZfbSign selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") ZfbSign record, @Param("example") ZfbSignExample example);

    int updateByExampleWithBLOBs(@Param("record") ZfbSign record, @Param("example") ZfbSignExample example);

    int updateByExample(@Param("record") ZfbSign record, @Param("example") ZfbSignExample example);

    int updateByPrimaryKeySelective(ZfbSign record);

    int updateByPrimaryKeyWithBLOBs(ZfbSign record);

    int updateByPrimaryKey(ZfbSign record);
}