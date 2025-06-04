package com.example.todolist.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.todolist.model.Task;

@Mapper
public interface ToDoMapper {

    @Select("SELECT * FROM tasks")
    List<Task> getAllTasks();
    
    @Select("SELECT * FROM tasks WHERE limit_date = #{limitDate}")
    List<Task> getSelectTasks(LocalDate limitDate);

    @Delete("DELETE FROM tasks WHERE task_id = #{taskId}")
    void deleteTask(long taskId);

    @Insert("INSERT INTO tasks(title, limit_date, description) VALUES (#{title}, #{limitDate}, #{description})")
    void insertTask(Task task);
}