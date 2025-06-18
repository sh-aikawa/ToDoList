package com.example.todolist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todolist.mapper.NexusMapper;
import com.example.todolist.model.Message;

@Repository
public class NexusRepository{
    private final NexusMapper nexusMapper;

    public NexusRepository(NexusMapper nexusMapper){
        this.nexusMapper = nexusMapper;
    }

    public List<Message> getChat(long myId, long userId){
        return nexusMapper.getChat(myId, userId);
    }

    public void sendMessage(Message message){
        nexusMapper.insertMessage(message);
    }    public void read(long chatId, long receiveUserId){
        nexusMapper.read(chatId, receiveUserId);
    }
}