package com.example.todolist.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todolist.form.CometForm;
import com.example.todolist.model.Comet;
import com.example.todolist.service.CometService;

@Controller
@RequestMapping("/comet")
public class CometController {

    private final CometService cometService;

    public CometController(CometService cometService) {
        this.cometService = cometService;
    }

    @GetMapping
    public String comets(Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒");
        List<Comet> comets = cometService.getAllComets();
        for (Comet comet : comets) {
            String formattedDate = comet.getCreatedAt().format(formatter);
            comet.setFormattedCreatedAt(formattedDate);
        }

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
