package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.todolist.form.UserForm;
import com.example.todolist.service.UserService;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/userRegister")
    public String showRegistrationForm(Model model){
        model.addAttribute("userForm",new UserForm());
        return "userRegister";
    }

    @PostMapping("/userRegister")
    public String registerUser(@ModelAttribute UserForm userForm){
        userService.createUser(userForm);
        return "redirect:/login?register";
    }
}
