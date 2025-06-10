package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.form.MessageForm;
import com.example.todolist.model.Message;
import com.example.todolist.repository.NexusRepository;

@Service
public class NexusService {
    private final NexusRepository nexusRepository;
    private final UserService userService;

    public NexusService(NexusRepository nexusRepository, UserService userService) {
        this.nexusRepository = nexusRepository;
        this.userService = userService;
    }

    public List<Message> getChat(long userId) {
        long myId = userService.getUserId();
        return nexusRepository.getChat(myId, userId);
    }

    public void sendMessage(MessageForm messageForm){
        Message message = new Message();
        message.setReceiveUserId(messageForm.getReceiveUserId());
        message.setContent(messageForm.getContent());

        long userId = userService.getUserId();
        message.setSendUserId(userId);
        System.out.println(message.getReceiveUserId()+","+message.getSendUserId());//0.1
        nexusRepository.sendMessage(message);
    }

}
