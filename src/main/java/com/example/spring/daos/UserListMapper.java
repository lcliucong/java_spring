package com.example.spring.daos;

import com.example.spring.pojo.BookModel;
import com.example.spring.pojo.UserList;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper层，dao层     Dao层调用entity层(Model层)
 * 对数据库实现持久化操作，在此Dao层中增删改查功能，与xx.xml文件对应
 */
@Mapper //必须加上注解，不然找不到
public interface UserListMapper {
    List<UserList> selectAll(UserList condition);
    List<UserList> getAll();
    UserList selectByUsername(String username);

    List<UserList> selectBy(UserList condition);
    int updateStatusById(UserList userList);
    UserList selectByPhone(String phone);
    String getRealPassword(String phone);
    void insertAll(UserList userList);
}
