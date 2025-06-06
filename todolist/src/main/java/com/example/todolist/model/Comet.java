package com.example.todolist.model;

import lombok.Data;

@Data
public class Comet {
    private long cometId;
    private long userId;
    private long username;
    private String content;
    private boolean happy;
}
