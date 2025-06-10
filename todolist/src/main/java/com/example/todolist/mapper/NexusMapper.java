package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Message;

@Mapper
public interface NexusMapper {

    @Select("SELECT * FROM chat")
    String getAllChat();

    @Select("SELECT send_user_id, receive_user_id, content, send_at, read FROM chat WHERE send_user_id = #{myId} AND receive_user_id = #{userId} OR send_user_id = #{userId} AND receive_user_id = #{myId} ORDER BY send_at ASC")
    List<Message> getChat(long myId, long userId);

    @Insert("INSERT INTO chat(send_user_id, receive_user_id, content) VALUES(#{sendUserId},#{receiveUserId},#{content})")
    void insertMessage(Message message);
}
