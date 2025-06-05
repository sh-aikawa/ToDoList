package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.service.ToDoService;

@Controller
@RequestMapping("/todolist")
public class ToDoController {

    private final RegistrationController registrationController;
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService, RegistrationController registrationController) {
        this.toDoService = toDoService;
        this.registrationController = registrationController;
    }

    @GetMapping
    public String toDos(Model model) {
        List<Task> tasks = toDoService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "Home";// 一覧画面に遷移
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        ToDoForm toDoForm = new ToDoForm();
        model.addAttribute("toDoForm", toDoForm);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(ToDoForm toDoForm) {
        toDoService.registerTask(toDoForm);

        return "redirect:/todolist";
    }

    @PostMapping("/search")
    public String getSelectTasks(@RequestParam("selectedDate") LocalDate selectedDate, Model model) {
        List<Task> tasks = toDoService.getSelectTasks(selectedDate);
        model.addAttribute("tasks", tasks);
        return "redirect:/todolist";
    }

    @PostMapping("/check")
    public String updateChecked(@RequestParam("taskId") long taskId,
            @RequestParam("checked") boolean checked) {
        toDoService.updateChecked(taskId, checked);
        return "redirect:/todolist";
    }

    @PostMapping("/finish")
    public String getFinishTasks(Model model) {
        List<Task> tasks = toDoService.getFinishTasks();
        model.addAttribute("tasks", tasks);
        return "completed";
    }

    @GetMapping("/detail")
    public String showTaskDetail(@RequestParam("taskId") Long taskId, Model model) {
        Task task = toDoService.getTask(taskId);
        model.addAttribute("task", task);
        return "detail";
    }

    @GetMapping("/taskEdit/{taskId}")
    public String taskEdit(@PathVariable long taskId, Model model) {
        Task task = toDoService.getTask(taskId);
        model.addAttribute("task",task);
        return "edit";
    }
    
    
}
