package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.service.ToDoService;




@Controller
@RequestMapping("/todolist")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public String toDos(Model model) {
        List<Task> tasks = toDoService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "Home";//一覧画面に遷移
    }
    
    @GetMapping("/register")
    public String getMethodName(Model model) {
        ToDoForm toDoForm = new ToDoForm();
        model.addAttribute("toDoForm",toDoForm);
        return "register";
    }
    
    @PostMapping("/register")
    public String postMethodName(ToDoForm toDoForm) {
        toDoService.registerTask(toDoForm);
        
        return "redirect:/todolist";
    }
    

}
