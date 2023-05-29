package com.example.spring.daos;

import com.example.spring.pojo.BookModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper  //添加Mapper注解，不然找不到
public interface BookMapper {

    //开启事务
    @Transactional(rollbackFor = Exception.class)
    int saves(BookModel bookModel);

    List<BookModel> getAll();
    int updateInfo(BookModel updateInfo);

    int delBook(int bookId);
    int softDel(int bookId);
    List<BookModel> selectBy(BookModel condition);
    List<BookModel> getByUser(BookModel condition);
    List<BookModel> getByname(BookModel condition);

}
