package com.example.todolist.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todolist.mapper.ToDoMapper;
import com.example.todolist.model.Task;

@Repository
public class ToDoRepository {
    private final ToDoMapper toDoMapper;
    
    public ToDoRepository(ToDoMapper toDoMapper){
        this.toDoMapper = toDoMapper;
    }

    public List<Task> getTasksByUserId(long userId){
        return toDoMapper.getTasksByUserId(userId);
    }

    public List<Task> getSelectTasks(LocalDate limitDate){
        return toDoMapper.getSelectTasks(limitDate);
    }

    public void updateChecked(long taskId, boolean checked){
        toDoMapper.updateChecked(taskId, checked);
    }

    public void insertTask(Task task){
        toDoMapper.insertTask(task);
    }

    public void deleteTask(long userId){
        toDoMapper.deleteTask(userId);
    }

    public List<Task> getFinishTasks() {
        return toDoMapper.getFinishTasks();
    }

    public Task getTask(long taskId) {
        return toDoMapper.getTask(taskId);
    }

    public void editTask(Task task){
        toDoMapper.editTask(task);
    }

    public List<Task> getSortDescTasks(long userId) {
        return toDoMapper.getSortDescTasks(userId);
    }

    public List<Task> getSortAscTasks(long userId) {
        return toDoMapper.getSortAscTasks(userId);
    }

    public List<Task> getTasksforRoulette(long userId) {
        return toDoMapper.getTasksforRoulette(userId);
    }

    public void deleteTaskByTaskId(long taskId){
        toDoMapper.deleteTaskByTaskId(taskId);
    }

}
