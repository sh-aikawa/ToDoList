package com.example.todolist.repository;

import org.springframework.stereotype.Repository;

import com.example.todolist.model.User;
import com.example.todolist.mapper.UserMapper;

@Repository
public class UserRepository{
    private final UserMapper userMapper;
    
    public UserRepository(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public User selectUserByUsername(String username){
        return userMapper.selectUserByUsername(username);
    }

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    public long getUserId(String username){
        return userMapper.getUserId(username);
    }

}