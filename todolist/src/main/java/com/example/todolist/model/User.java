package com.example.todolist.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private LocalDate createdAt;
}
