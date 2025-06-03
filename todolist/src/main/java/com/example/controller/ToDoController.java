package com.example.controller;

import com.example.service.ToDoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/toDos")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public String toDos(Model model) {
        List<ToDo> toDos = toDoService.getAllToDos();
        model.addAllAttributes("toDos", toDos);
        return "templates/index.html";//一覧画面に遷移
    }
    

}
