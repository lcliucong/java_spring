package com.example.spring.service.impl;

import com.example.spring.daos.BookMapper;
import com.example.spring.pojo.BookModel;
import com.example.spring.service.interfaces.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saves(BookModel bookModel){
            return bookMapper.saves(bookModel);
    }

    @Override
    public List<BookModel> getAll() {
        return bookMapper.getAll();
    }

    @Override
    public int updateInfo(BookModel updateInfo) {
        return bookMapper.updateInfo(updateInfo);
    }

    @Override
    public int delBook(int bookId) {
        return bookMapper.delBook(bookId);
    }
    @Override
    public int softDel(int bookId){
        return bookMapper.softDel(bookId);
    }

    @Override
    public List<BookModel> selectBy(BookModel condition) {
        return bookMapper.selectBy(condition);
    }

    @Override
    public List<BookModel> getByUser(BookModel condition) {
        return bookMapper.getByUser(condition);
    }

    @Override
    public List<BookModel> getByname(BookModel condition) {
        return bookMapper.getByname(condition);
    }
}
