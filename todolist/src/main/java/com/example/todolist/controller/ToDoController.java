package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.service.ToDoService;

@Controller
@RequestMapping("/todolist")
public class ToDoController {

    private final ToDoService toDoService;;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public String toDos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Task> tasks = toDoService.getTasksByUserId(username);
        model.addAttribute("tasks", tasks);
        return "toDo/Home";// 一覧画面に遷移
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        ToDoForm toDoForm = new ToDoForm();
        model.addAttribute("toDoForm", toDoForm);
        return "toDo/register";
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
        return "toDo/Home";
    }

    @PostMapping("/check")
    public String updateChecked(@RequestParam("taskId") long taskId,
            @RequestParam("checked") boolean checked) {
        toDoService.updateChecked(taskId, checked);
        return "redirect:/todolist";
    }

    @GetMapping("/finish")
    public String getFinishTasks(Model model) {
        List<Task> tasks = toDoService.getFinishTasks();
        model.addAttribute("tasks", tasks);
        return "toDo/completed";
    }

    @GetMapping("/detail")
    public String showTaskDetail(@RequestParam("taskId") Long taskId, Model model) {
        Task task = toDoService.getTask(taskId);
        model.addAttribute("task", task);
        return "toDo/detail";
    }

    @GetMapping("/taskEdit/{taskId}")
    public String taskEdit(@PathVariable long taskId, Model model) {
        Task task = toDoService.getTask(taskId);
        model.addAttribute("task", task);
        return "toDo/edit";
    }

    @PostMapping("/{taskId}/edit")
    public String postEditTask(@PathVariable long taskId, @ModelAttribute("task") Task task) {
        task.setTaskId(taskId);
        toDoService.editTask(task);
        return "redirect:/todolist";
    }

    @GetMapping("/sortDesc")
    public String getSortAscTasks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Task> tasks = toDoService.getSortDescTasks(username);

        model.addAttribute("tasks", tasks);
        return "toDo/Home";
    }

    @GetMapping("/sortAsc")
    public String getSortDescTasks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Task> tasks = toDoService.getSortAscTasks(username);

        model.addAttribute("tasks", tasks);
        return "toDo/Home";
    }
    
    @GetMapping("/listroulette")
    public String getlistRoulette(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Random random = new Random();
        List<Task> tasks = toDoService.getTasksforRoullete(username);
        if(tasks.size() > 0){
            Task task = tasks.get(random.nextInt(tasks.size()));
            model.addAttribute("task", task);
        }else{
            Task task = new Task();
            task.setTitle("ありません！！！！");
            model.addAttribute("task", task);
        }
        return "listroulette";
    }

}
