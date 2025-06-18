package com.example.todolist.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.form.MessageForm;
import com.example.todolist.model.Message;
import com.example.todolist.model.User;
import com.example.todolist.service.NexusService;
import com.example.todolist.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/nexus")
public class NexusController {
    private final UserService userService;
    private final NexusService nexusService;

    public NexusController(UserService userService, NexusService nexusService) { // *NexusControllerのコンストラクタ */
        this.userService = userService;
        this.nexusService = nexusService;
    }

    @GetMapping
    public String selectUser(Model model) {
        List<User> users = userService.getAllFriends();
        model.addAttribute("users", users);
        return "nexus/select";
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable long id, Model model) {
        MessageForm messageForm = new MessageForm();
        messageForm.setReceiveUserId(id);
        List<Message> chat = nexusService.getChat(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒");
        for (Message message : chat) {
            String formattedDate = message.getSendAt().format(formatter);
            message.setFormattedSendAt(formattedDate);
        }
        // トーク相手のユーザー情報を取得
        User partner = userService.getAllFriends().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("chat", chat);
        model.addAttribute("messageForm", messageForm);
        model.addAttribute("partner", partner); // 追加
        return "nexus/chat";
    }

    @PostMapping("/chat/send")
    public String sendMessage(@ModelAttribute @Valid MessageForm messageForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) return "redirect:/nexus/chat/{userId}";
        nexusService.sendMessage(messageForm); // *受け取ったmessageFormをもとにdbに保存 */
        redirectAttributes.addAttribute("userId", messageForm.getReceiveUserId()); // *トーク画面にリダイレクトするためにトーク相手のIdをリダイレクト先へ
        return "redirect:/nexus/chat/{userId}";
    }

    @GetMapping(value = "/chat/{id}", params = "fragment=true")
    public String chatFragment(@PathVariable long id, Model model) {
        MessageForm messageForm = new MessageForm();
        messageForm.setReceiveUserId(id);
        List<Message> chat = nexusService.getChat(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒");
        for (Message message : chat) {
            String formattedDate = message.getSendAt().format(formatter);
            message.setFormattedSendAt(formattedDate);
        }
        User partner = userService.getAllFriends().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("chat", chat);
        model.addAttribute("messageForm", messageForm);
        model.addAttribute("partner", partner);
        return "nexus/chat :: chat-list";
    }

}
