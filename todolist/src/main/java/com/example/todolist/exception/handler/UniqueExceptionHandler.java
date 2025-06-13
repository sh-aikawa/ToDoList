package com.example.todolist.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.exception.UniqueException;

@RestControllerAdvice
public class UniqueExceptionHandler {
    @ExceptionHandler(UniqueException.class)
    public String handleUniqueException(UniqueException e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "アカウントIDは既に使用されています。");
        return "redirect:/userRegister";
    }
}
