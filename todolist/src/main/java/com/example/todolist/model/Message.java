package com.example.todolist.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Message {
    private long sendUserId;
    private long receiveUserId;
    private String content;
    private boolean read;
    @DateTimeFormat(pattern = "yyyy年M月d日 H時m分ss秒")
    private LocalDateTime sendAt;
    private String formattedSendAt;
}
