package com.lanswon.authserver.dao;


import com.lanswon.authserver.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectOneByName(@Param("tableName")String tableName, @Param("name")String name);

    User selectOneByPhone(@Param("tableName")String tableName,@Param("phone")String phone);

    User selectOneByEmail(@Param("tableName")String tableName,@Param("email")String email);

    int  insertUser(User user);

    int updateUser(User user);
}