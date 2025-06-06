package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Comet;

@Mapper
public interface CometMapper {
    @Select("SELECT * FROM comets")
    List<Comet> getAllComets();

    @Insert("INSERT INTO comets(user_id, content) VALUES (#{userId}, #{content})")
    void insertComet(Comet comet);
}
