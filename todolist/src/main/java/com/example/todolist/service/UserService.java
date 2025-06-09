package com.example.todolist.service;

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
        User user = new User();
        user.setUsername(userForm.getUsername());

        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);

        userRepository.insertUser(user);
    }

    public Long getUserId() {
        return userRepository.getUserId(getUsername());
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
