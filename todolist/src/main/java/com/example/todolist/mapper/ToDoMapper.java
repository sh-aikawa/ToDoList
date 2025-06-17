package com.example.todolist.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.todolist.model.Task;

@Mapper
public interface ToDoMapper {

    @Select("SELECT * FROM tasks WHERE checked = false AND user_id = #{userId}")
    List<Task> getTasksByUserId(long userId);

    @Select("SELECT * FROM tasks WHERE limit_date = #{limitDate} AND checked = false ORDER BY importance ASC")
    List<Task> getSelectTasks(LocalDate limitDate);

    @Delete("DELETE FROM tasks WHERE user_id = #{userId} AND checked = true")
    void deleteTask(long userId);

    @Insert("INSERT INTO tasks(user_id, title, limit_date, description, importance) VALUES (#{userId},#{title}, #{limitDate}, #{description}, #{importance})")
    void insertTask(Task task);

    @Update("UPDATE tasks SET checked = #{checked} WHERE task_id = #{taskId}") // checkedを更新
    void updateChecked(long taskId, boolean checked);

    @Select("SELECT * FROM tasks WHERE checked = true")
    List<Task> getFinishTasks();

    @Select("SELECT * FROM tasks WHERE task_id = #{taskId}")
    Task getTask(long taskId);

    @Update("UPDATE tasks SET title = #{title}, limit_date = #{limitDate}, description = #{description}, importance = #{importance} WHERE task_id = #{taskId}")
    void editTask(Task task);

    @Select("SELECT * FROM tasks WHERE checked = false AND user_id = #{userId}  ORDER BY limit_date DESC")
    List<Task> getSortDescTasks(long userId);

    @Select("SELECT * FROM tasks WHERE checked = false AND user_id = #{userId}  ORDER BY limit_date ASC")
    List<Task> getSortAscTasks(long userId);

    @Select("SELECT * FROM tasks WHERE user_id = #{userId} AND checked = false")
    List<Task> getTasksforRoulette(long userId);

    @Delete("DELETE FROM tasks WHERE task_id = #{taskId}")
    void deleteTaskByTaskId(long taskId);

}