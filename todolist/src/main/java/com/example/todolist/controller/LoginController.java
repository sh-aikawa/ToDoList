package com.example.todolist.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todolist.security.CustomUserDetails;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String Login(HttpSession session) {
        session.setAttribute("isComplete", false);
        return "login";
    }

    @GetMapping("/effect")
    public String effect(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        model.addAttribute("accountName", userDetails.getAccountName());
        return "effect";
    }
}
