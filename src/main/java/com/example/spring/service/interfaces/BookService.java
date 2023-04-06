package com.example.spring.service.interfaces;

import com.example.spring.pojo.BookModel;

import java.util.List;

public interface BookService {

    int saves(BookModel bookModel);
    List<BookModel> getAll();

    int updateInfo(BookModel updateInfo);

    int delBook(int bookId);
    int softDel(int bookId);
    List<BookModel> selectBy(BookModel condition);
    List<BookModel> getByUser(BookModel condition);
    List<BookModel> getByname(BookModel condition);
}
