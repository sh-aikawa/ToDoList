package com.example.todolist.exception;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Exception {

    @NotBlank(message = "予定は必須です。")
    @Size(max = 100, message = "予定は100文字以内で入力してください。")
    private String title;

    @Size(max = 5000, message = "詳細は5000文字以内で入力してください。")
    private String description;

    private LocalDate limitDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate){
        this.limitDate = limitDate;
    }
}
