package com.example.spring.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;

public class PageUtils<T> {
    public HashMap<String,Object> returnMake(PageInfo<T> pageInfo){
        HashMap<String,Object> returns = new HashMap<>();
        returns.put("total",pageInfo.getTotal());
        returns.put("pageSize", pageInfo.getPageSize());
        returns.put("pageNum", pageInfo.getPageNum());
        returns.put("list",pageInfo.getList());
        return returns;
    }
}
