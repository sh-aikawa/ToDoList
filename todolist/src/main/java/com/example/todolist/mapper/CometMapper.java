package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Comet;

@Mapper
public interface CometMapper {
    @Select("SELECT comets.*, users.username FROM comets JOIN users ON comets.user_id = users.user_id")
    List<Comet> getAllComets();

    @Insert("INSERT INTO comets(user_id, content) VALUES (#{userId}, #{content})")
    void insertComet(Comet comet);

}
