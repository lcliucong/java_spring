package com.example.spring.filter;

public class Filters {
    private int page = 1;
    private int size = 20;
    private  String orderBy = "id desc";
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }



    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

