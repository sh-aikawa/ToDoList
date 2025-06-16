package com.example.todolist.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private long taskId;
    private long userId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDate;
    private String description;
    private boolean checked;
    private String importance;
}