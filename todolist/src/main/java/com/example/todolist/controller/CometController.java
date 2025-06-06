package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todolist.form.CometForm;
import com.example.todolist.model.Comet;
import com.example.todolist.service.CometService;
import com.example.todolist.service.UserService;

@Controller
@RequestMapping("/comet")
public class CometController {

    private final CometService cometService;
    private final UserService userService;

    public CometController(CometService cometService, UserService userService){
        this.cometService = cometService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String comets(Model model) {
        /*
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long userId = userService.getUserId();
        */
        List<Comet> comets = cometService.getAllComets();
        model.addAttribute("comets", comets);
        return "comet/home";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        CometForm cometForm = new CometForm();
        model.addAttribute("cometForm", cometForm);
        return "comet/register";
    }

    @PostMapping("/register")
    public String postRegister(CometForm cometForm) {
        cometService.registerComet(cometForm);

        return "redirect:/comet";
    }
}
