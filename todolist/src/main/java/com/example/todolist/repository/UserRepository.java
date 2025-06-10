package com.example.todolist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todolist.mapper.UserMapper;
import com.example.todolist.model.User;

@Repository
public class UserRepository {
    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public long getUserId(String username) {
        return userMapper.getUserId(username);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public List<User> getAllFriends(long userId){
        return userMapper.getAllFriends(userId);
    }
}