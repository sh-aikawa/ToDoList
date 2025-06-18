package com.example.todolist.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private long chatId;
    private long sendUserId;
    private long receiveUserId;
    private String content;
    @DateTimeFormat(pattern = "yyyy年M月d日 H時m分ss秒")
    private LocalDateTime sendAt;
    private String formattedSendAt;
    private boolean read;

    public LocalDateTime getJSTSendAt() {
        return sendAt != null ? sendAt.plusHours(9) : null;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt != null ? sendAt.minusHours(9) : null;
    }
}
