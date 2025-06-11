package com.example.todolist.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    public NexusController(UserService userService, NexusService nexusService) {    //*NexusControllerのコンストラクタ */
        this.userService = userService;
        this.nexusService = nexusService;
    }

    @GetMapping
    public String selectUser(Model model) {
        List<User> users = userService.getAllFriends(); //*自分以外のユーザの一覧を取得 */
        model.addAttribute("users", users); //*usersをフロントへ */
        return "/nexus/select";
    }

    @GetMapping("/chat/{userId}")
    public String chat(@PathVariable long userId, Model model) {
        MessageForm messageForm = new MessageForm();    //*新規メッセージ投稿時のデータを格納するmessageFormを作成 */
        messageForm.setReceiveUserId(userId);   //*メッセージの受け取り手のIDにトーク相手のIDを設定 */
        List<Message> chat = nexusService.getChat(userId);  //*トーク相手とログインユーザー間のmessageを一覧で取得 */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒"); //*messageの送信日時のフォーマット(yyyy年M月d日 H時m分s秒) */
        for (Message message : chat) {
            String formattedDate = message.getSendAt().format(formatter);   //*messageの送信日時をフォーマット */
            message.setFormattedSendAt(formattedDate);
        }
        model.addAttribute("chat", chat);   //*messageの一覧をフロントへ */
        model.addAttribute("messageForm", messageForm); //*messageFormをフロントへ */
        return "nexus/chat";
    }

    @PostMapping("/chat/send")
    public String sendMessage(MessageForm messageForm, RedirectAttributes redirectAttributes) {
        nexusService.sendMessage(messageForm);  //*受け取ったmessageFormをもとにdbに保存 */
        redirectAttributes.addAttribute("userId", messageForm.getReceiveUserId());  //*トーク画面にリダイレクトするためにトーク相手のIdをリダイレクト先へ */
        return "redirect:/nexus/chat/{userId}";
    }

}
