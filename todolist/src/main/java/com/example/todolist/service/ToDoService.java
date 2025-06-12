package com.example.todolist.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ToDoRepository;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;
    private final UserService userService;

    public ToDoService(ToDoRepository toDoRepository, UserService userService){
        this.toDoRepository = toDoRepository;
        this.userService = userService;
    }

    public List<Task> getAllTasks(){
        return toDoRepository.getAllTasks();
    }

    public List<Task> getTasksByUserId(String username){
        long userId  = userService.getId();
        return toDoRepository.getTasksByUserId(userId);
    }

    public List<Task> getSelectTasks(LocalDate limitDate){
        return toDoRepository.getSelectTasks(limitDate);
    }

    public void updateChecked(long taskId, boolean checked){
        toDoRepository.updateChecked(taskId, checked);
    }

    public void deleteTask(String username){
        long userId  = userService.getId();
        toDoRepository.deleteTask(userId);
    }

    public void registerTask(ToDoForm toDoForm){
        Task task = new Task();
        task.setTitle(toDoForm.getTitle());
        task.setLimitDate(toDoForm.getLimitDate());
        task.setDescription(toDoForm.getDescription());
        task.setImportance(toDoForm.getImportance());

        long userId = userService.getId();
        task.setUserId(userId);
        toDoRepository.insertTask(task);
    }

    public List<Task> getFinishTasks() {
        return toDoRepository.getFinishTasks();
    }

    public Task getTask(long taskId) {
        return toDoRepository.getTask(taskId);
    }

    public void editTask(Task task) {
        toDoRepository.editTask(task);
    }

    public List<Task> getSortDescTasks(String username) {
        long userId  = userService.getId();
        return toDoRepository.getSortDescTasks(userId);
    }

    public List<Task> getSortAscTasks(String username) {
        long userId  = userService.getId();
        return toDoRepository.getSortAscTasks(userId);
    }

    public List<Task> getTasksforRoullete(String username) {
        long userId  = userService.getId();
        return toDoRepository.getTasksforRoullete(userId);
    }

    public void deleteTaskByTaskId(long taskId){
        long userId = userService.getId();
        toDoRepository.deleteTaskByTaskId(userId, taskId);
    }
}
