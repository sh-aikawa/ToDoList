package com.example.todolist.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.todolist.security.CustomUserDetails;
import com.example.todolist.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String Login(HttpSession session) {
        session.setAttribute("isComplete", false);
        return "login";
    }

    @GetMapping("/effect")
    public String effect(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Boolean inFirstVisit = userService.getInFirstVisit();
        model.addAttribute("accountName", userDetails.getAccountName());
        model.addAttribute("inFirstVisit", inFirstVisit);
        return "effect";
    }

    @GetMapping("/updateFlag")
    @ResponseBody
    public Map<String, Object> updateFlag() {
        userService.setInFirstVisit();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Update successful");
        return response;
    }
}
