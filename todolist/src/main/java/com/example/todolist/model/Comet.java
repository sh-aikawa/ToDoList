package com.example.todolist.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Comet {
    private long cometId;
    private long userId;
    private String accountName;
    private String accountId;
    private String content;
    private boolean happy;
    @DateTimeFormat(pattern = "yyyy年M月d日 H時m分ss秒")
    private LocalDateTime createdAt;
    private String formattedCreatedAt;
}
