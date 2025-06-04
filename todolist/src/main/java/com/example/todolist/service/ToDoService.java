package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ToDoRepository;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    public List<Task> getAllTasks(){
        return toDoRepository.getAllTasks();
    }

    public void deleteTask(long taskId){
        toDoRepository.deleteTask(taskId);
    }

    public void registerTask(ToDoForm toDoForm){
        Task task = new Task();
        task.setTitle(toDoForm.getTitle());
        task.setLimitDate(toDoForm.getLimitDate());
        task.setDescription(toDoForm.getDescription());
        toDoRepository.insertTask(task);
    }

}
