package com.example.todolist.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String accountName;
    private String accountId;
    private String password;
    private LocalDate createdAt;
    private long unreadCount;
}
