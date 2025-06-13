package com.example.todolist.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.example.todolist.model.User;
import com.example.todolist.security.CustomUserDetails;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    @GetMapping("/efect")
    public String efect(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        model.addAttribute("accountName", userDetails.getAccountName());
        return "efect";
    }
}
