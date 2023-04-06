package com.example.spring.filter;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookListFilter extends Filters{

    private int id;
    private String bookName;
    private String bookNumber;
    private int bookAmount;
    private int bookStatus;
    private Date bookYear;
    private String bookAuth;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public int getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(int bookAmount) {
        this.bookAmount = bookAmount;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Date getBookYear() {
        return bookYear;
    }

    public void setBookYear(Date bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookAuth() {
        return bookAuth;
    }

    public void setBookAuth(String bookAuth) {
        this.bookAuth = bookAuth;
    }
}
