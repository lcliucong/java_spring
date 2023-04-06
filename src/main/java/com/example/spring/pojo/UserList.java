package com.example.spring.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * model层
 * 实体层，实体类
 * 用来映射数据库表字段，实现set，get方法
 */
public class UserList {


    private int id;
    private int bookid;
    private String username;
    private String password;
    private String phone;
    private int sex;
    private int status;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setBookid(int bookid){
        this.bookid = bookid;
    }
    public int getBookid(){
        return bookid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return status;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    public Date getUpdateTime(){
        return this.updateTime;
    }
}
