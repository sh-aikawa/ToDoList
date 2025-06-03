package com.example.todolist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Task;

@Mapper
public interface ToDoMapper {

    @Select("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Delete("DELETE FROM tasks WHERE task_id = #{taskId}")
    void deleteTask(long taskId);
}