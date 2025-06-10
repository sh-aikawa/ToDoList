package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.User;

@Mapper
public interface UserMapper {

    @Select("SELECT user_id,username,password FROM users WHERE username = #{username}")
    User selectUserByUsername(String username);

    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertUser(User user);

    @Select("SELECT user_id FROM users WHERE username = #{username}")
    long getUserId(String username);

    @Select("SELECT username FROM users WHERE user_id = #{userId}")
    String getUsername(long userId);

    @Select("SELECT user_id,username FROM users")
    List<User> getAllUsers();

    @Select("SELECT user_id, username FROM users WHERE user_id != #{userId}")
    List<User> getAllFriends(long userId);
}
