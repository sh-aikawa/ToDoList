package com.example.todolist.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageForm {
    private long receiveUserId;
    @Size(max = 4000, message = "4000字以内で入力してください")
    private String content;
}
