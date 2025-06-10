package com.example.todolist.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todolist.form.MessageForm;
import com.example.todolist.model.Message;
import com.example.todolist.model.User;
import com.example.todolist.service.NexusService;
import com.example.todolist.service.UserService;

@Controller
@RequestMapping("/nexus")
public class NexusController {
    private final UserService userService;
    private final NexusService nexusService;

    public NexusController(UserService userService, NexusService nexusService) {
        this.userService = userService;
        this.nexusService = nexusService;
    }

    @GetMapping
    public String selectUser(Model model) {
        List<User> users = userService.getAllFriends();
        model.addAttribute("users", users);
        return "/nexus/select";
    }

    @GetMapping("/chat/{userId}")
    public String chat(@PathVariable long userId, Model model) {
        MessageForm messageForm = new MessageForm();
        messageForm.setReceiveUserId(userId);
        List<Message> chat = nexusService.getChat(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒");
        for (Message message : chat) {
            String formattedDate = message.getSendAt().format(formatter);
            message.setFormattedSendAt(formattedDate);
        }
        model.addAttribute("chat", chat);
        model.addAttribute("messageForm", messageForm);
        return "nexus/chat";
    }

    @PostMapping("/chat/send")
    public String sendMessage(MessageForm messageForm) {
        nexusService.sendMessage(messageForm);
        return "redirect:/nexus";
    }

}
