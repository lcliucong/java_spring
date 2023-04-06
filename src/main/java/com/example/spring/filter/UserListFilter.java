package com.example.spring.filter;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserListFilter{
    private int sex;
//    @NotBlank(message = "手机号不能为空")
//    @Length(min = 3, max = 11, message = "长度不太对")
    private String username;
    @NotBlank(message = "手机号不能为空")
    private String phone;
//    @NotBlank(message = "不为空")
//    @Length(min = 3,message = "长度最小为3")
    private String tester;
    private int status;
    private int id;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度最短为6位")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getTester(){return this.tester;}
    public void setTester(String tester){
        this.tester = tester;
    }
}

