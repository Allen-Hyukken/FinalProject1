package com.profilewebsite.finalproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.profilewebsite.finalproject.service.QuizService;

@Controller
public class HomeController {
    private final QuizService quizService;
    public HomeController(QuizService quizService){ this.quizService = quizService; }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("quizzes", quizService.findAll());
        return "index";
    }
}

