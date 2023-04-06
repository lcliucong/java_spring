package com.example.spring.service.interfaces;

import com.example.spring.pojo.UserList;

import java.util.List;

//接口类
public interface UserListService {

    List<UserList> selectAll( UserList condition );
    List<UserList> getAll();
    UserList selectByUsername(String username);
    List<UserList> selectBy(UserList condition);

    int updateStatusById(UserList userList);
    UserList selectByPhone(String phone);
    public String getRealPassword(String phone);
}
