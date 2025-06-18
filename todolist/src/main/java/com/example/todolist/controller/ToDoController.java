package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.form.ToDoForm;
import com.example.todolist.model.Task;
import com.example.todolist.service.ToDoService;
import com.example.todolist.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/todolist")
public class ToDoController {

    private final ToDoService toDoService;
    private final UserService userService;

    public ToDoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @GetMapping
    public String toDos(HttpSession session, Model model) {
        session.setAttribute("isComplete", false);
        List<Task> tasks = toDoService.getTasks();
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
    public String postRegister(@ModelAttribute @Valid ToDoForm toDoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "toDo/register";
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
    public String getFinishTasks(HttpSession session, Model model) {
        List<Task> tasks = toDoService.getFinishTasks();
        session.setAttribute("isComplete", true);
        model.addAttribute("tasks", tasks);
        return "toDo/completed";
    }

    @GetMapping("/taskEdit/{taskId}")
    public String taskEdit(@PathVariable long taskId, Model model) {
        Task task = toDoService.getTask(taskId);
        model.addAttribute("task", task);
        return "toDo/edit";
    }

    @PostMapping("/{taskId}/edit")
    public String postEditTask(@PathVariable long taskId, @ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "toDo/edit";
        task.setTaskId(taskId);
        toDoService.editTask(task);
        return "redirect:/todolist";
    }

    @GetMapping("/sortDesc")
    public String getSortAscTasks(Model model) {
        List<Task> tasks = toDoService.getSortDescTasks();

        model.addAttribute("tasks", tasks);
        return "toDo/Home";
    }

    @GetMapping("/sortAsc")
    public String getSortDescTasks(Model model) {
        List<Task> tasks = toDoService.getSortAscTasks();

        model.addAttribute("tasks", tasks);
        return "toDo/Home";
    }

    @GetMapping("/roulette_effect")
    public String getRoulette_effect(Model model) {
        List<Task> tasks = toDoService.getTasks();
        model.addAttribute("tasks", tasks);
        return "roulette_effect";
    }

    @GetMapping("roulette_effect/listRoulette")
    public String getListRoulette(Model model) {
        Random random = new Random();
        List<Task> tasks = toDoService.getTasksforRoulette();
        if (tasks.size() > 0) {
            Task task = tasks.get(random.nextInt(tasks.size()));
            model.addAttribute("task", task);
        } else {
            Task task = new Task();
            task.setTitle("ありません！！！！");
            model.addAttribute("task", task);
        }
        return "listRoulette";
    }

    @GetMapping("/setting")
    public String setting(){
        return "toDo/setting";
    }

    //こっちの削除処理を使用している
    @GetMapping("/finishDeleteByTaskId")
    public String finishDeleteByTaskId(@RequestParam long taskId, HttpSession session) {
        long Id = taskId;
        toDoService.deleteTaskByTaskId(Id);
        String result = "";
        Boolean isComplete = (Boolean) session.getAttribute("isComplete");
        if (Boolean.TRUE.equals(isComplete)) {
            result = "redirect:/todolist/finish";
        }else{
            result = "redirect:/todolist";
        }
        return result;
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount() {
        userService.deleteUser();
        return "redirect:/login?deleted";
    }
}