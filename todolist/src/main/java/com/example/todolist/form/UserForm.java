package com.example.todolist.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
    @NotBlank(message = "アカウント名を入力してください")
    private String accountName;
    @NotBlank(message = "アカウントIDを入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "アカウントIDは半角英数字のみ使用できます")
    private String accountId;
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 4, max = 16, message = "パスワードは4文字以上16字以内で設定してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数字のみ使用できます")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,16}$", message = "パスワードは英字と数字を含めてください")
    private String password;
}
