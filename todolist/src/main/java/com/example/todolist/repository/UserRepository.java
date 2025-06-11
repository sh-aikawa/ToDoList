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

    public User selectUserByAccountId(String accountId) {
        return userMapper.selectUserByAccountId(accountId);
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public long getId(String accountId) {
        return userMapper.getId(accountId);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllFriends(-1); // 仮: 全ユーザー取得用
    }

    public List<User> getAllFriends(long id){
        return userMapper.getAllFriends(id);
    }

    public String getAccountName(Long id) {
        return userMapper.getAccountName(id);
    }
}