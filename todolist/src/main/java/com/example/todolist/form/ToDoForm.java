package com.example.todolist.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ToDoForm {
    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 100, message = "タイトルは100字以内で入力してください")
    private String title;
    private LocalDate limitDate;
    @Size(max = 5000,message = "詳細は5000字以内で入力してください")
    private String description;
    private int importance;
}
