package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Comet;

@Mapper
public interface CometMapper {
    @Select("SELECT c.comet_id AS cometId, c.user_id AS userId, c.content, c.happy, c.created_at AS createdAt, u.account_name AS accountName, u.account_id AS accountId " +
            "FROM comets c JOIN users u ON c.user_id = u.id " +
            "ORDER BY c.created_at DESC")
    List<Comet> getAllComets();

    @Insert("INSERT INTO comets(user_id, content) VALUES (#{userId}, #{content})")
    void insertComet(Comet comet);

}
