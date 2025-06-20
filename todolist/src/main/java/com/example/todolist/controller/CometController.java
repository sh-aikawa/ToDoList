package com.example.todolist.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todolist.form.CometForm;
import com.example.todolist.model.Comet;
import com.example.todolist.service.CometService;
import com.example.todolist.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comet")
public class CometController {

    private final CometService cometService;
    private final UserService userService;

    public CometController(CometService cometService, UserService userService) { //*CometControllerのコンストラクタ */
        this.cometService = cometService;
        this.userService = userService;
    }

    @GetMapping
    public String comets(Model model) {
        long myId = userService.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 H時m分s秒"); //*投稿日時のフォーマット(yyyy年M月d日 H時m分s秒) */
        List<Comet> comets = cometService.getAllComets(); //*cometの一覧を取得 */
        for (Comet comet : comets) {
            String formattedDate = comet.getJSTCreatedAt().format(formatter); // *投稿日時をフォーマット */
            comet.setFormattedCreatedAt(formattedDate);
        }

        model.addAttribute("myId", myId);
        model.addAttribute("comets", comets);//*cometsをフロントへ */
        return "comet/home";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        CometForm cometForm = new CometForm(); // *新規comet作成時のデータを格納するcometFormを作成 */
        model.addAttribute("cometForm", cometForm);// *空のままフロントへ */
        return "comet/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute @Valid CometForm cometForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "comet/register";
        }
        cometService.registerComet(cometForm);// *受け取ったcometFormをもとにdbに格納 */

        return "redirect:/comet";
    }

    @GetMapping("/delete")
    public String deleteComet(@RequestParam long cometId) {
        cometService.deleteComet(cometId);
        return "redirect:/comet";
    }
}
