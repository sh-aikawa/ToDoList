package com.example.todolist.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private long taskId;
    private long userId;
    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 100, message = "タイトルは100字以内で入力してください")
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDate;
    @Size(max = 5000, message = "詳細は5000字以内で入力してください")
    private String description;
    private boolean checked;
    private int importance;
}