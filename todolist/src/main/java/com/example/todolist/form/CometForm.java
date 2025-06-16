package com.example.todolist.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CometForm {
    @Size(max = 280,message = "280字以内で入力してください")
    @NotBlank(message = "内容を入力してください")
    private String content;
}