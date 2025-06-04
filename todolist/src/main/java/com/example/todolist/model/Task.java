package com.example.todolist.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Task {
    private long taskId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDate;
    private String description;
    private boolean checked;
}
