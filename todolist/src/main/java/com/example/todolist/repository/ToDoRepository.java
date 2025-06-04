package com.example.todolist.repository;

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

    public List<Task> getAllTasks(){
        return toDoMapper.getAllTasks();
    }

    public void insertTask(Task task){
        toDoMapper.insertTask(task);
    }

    public void deleteTask(long taskId){
        toDoMapper.deleteTask(taskId);
    }

}
