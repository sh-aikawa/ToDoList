package com.example.todolist.form;

import lombok.Data;

@Data
public class MessageForm {
    private long receiveUserId;
    private String content;
}
