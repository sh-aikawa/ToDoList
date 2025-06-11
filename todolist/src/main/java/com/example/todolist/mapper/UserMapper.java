package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.User;

@Mapper
public interface UserMapper {
    @Select("SELECT id, account_name, account_id, password FROM users WHERE account_id = #{accountId}")
    User selectUserByAccountId(String accountId);

    @Insert("INSERT INTO users (account_name, account_id, password) VALUES (#{accountName}, #{accountId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT id FROM users WHERE account_id = #{accountId}")
    long getId(String accountId);

    @Select("SELECT account_name FROM users WHERE id = #{id}")
    String getAccountName(long id);

    @Select("SELECT id, account_name, account_id FROM users WHERE id != #{id}")
    List<User> getAllFriends(long id);
}
