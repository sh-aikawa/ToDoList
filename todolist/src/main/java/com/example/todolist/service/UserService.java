package com.example.todolist.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todolist.form.UserForm;
import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserForm userForm) {
        // 既存アカウントIDの重複チェック
        if (userRepository.selectUserByAccountId(userForm.getAccountId()) != null) {
            throw new com.example.todolist.exception.UniqueException("アカウントIDは既に使用されています。");
        }
        User user = new User();
        user.setAccountName(userForm.getAccountName());
        user.setAccountId(userForm.getAccountId());
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);

        userRepository.insertUser(user);
    }

    public Long getId() {
        return userRepository.getId(getAccountId());
    }
    public String getAccountId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public String getAccountName() {
        Long id = getId();
        return userRepository.getAccountName(id);
    }

    public List<User> getAllFriends(){
        long id = getId();
        return userRepository.getAllFriends(id);
    }

    public boolean getInFirstVisit(){
        long id = getId();
        return userRepository.getInFirstVisit(id);
    }

    public void setInFirstVisit() {
        long id = getId();
        userRepository.setInFirstVisit(id);
    }



}
