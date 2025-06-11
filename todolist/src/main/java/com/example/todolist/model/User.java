package com.example.todolist.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String accountName;
    private String accountId;
    private String password;
    private LocalDate createdAt;

    // 明示的なゲッター/セッター（用途に応じて追加）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
}
