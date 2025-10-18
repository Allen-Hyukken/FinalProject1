package com.profilewebsite.finalproject.controller;


import org.springframework.web.bind.annotation.*;
import com.profilewebsite.finalproject.service.QuizService;
import com.profilewebsite.finalproject.model.Quiz;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final QuizService quizService;
    public ApiController(QuizService quizService){ this.quizService = quizService; }

    @GetMapping("/quizzes")
    public List<Quiz> getAll(){ return quizService.findAll(); }
}

