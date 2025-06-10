package com.example.todolist.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ToDoForm {
    private String title;
    private LocalDate limitDate;
    private String description;
    private String importance;
}
