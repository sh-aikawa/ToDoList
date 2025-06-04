package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getRegister(Model model) {
        ToDoForm toDoForm = new ToDoForm();
        model.addAttribute("toDoForm",toDoForm);
        return "register";
    }
    
    @PostMapping("/register")
    public String postRegister(ToDoForm toDoForm) {
        toDoService.registerTask(toDoForm);
        
        return "redirect:/todolist";
    }
    
    @GetMapping("/search")
    public String getSelectTasks(@RequestParam("selectedDate") LocalDate selectedDate , Model model) {
        List<Task> tasks = toDoService.getSelectTasks(selectedDate);
        model.addAttribute("tasks", tasks);
        return "Home";
    }
    

}
