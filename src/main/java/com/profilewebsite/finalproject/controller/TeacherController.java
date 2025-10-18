package com.profilewebsite.finalproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.profilewebsite.finalproject.service.QuizService;
import com.profilewebsite.finalproject.model.Quiz;
import java.security.Principal;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final QuizService quizService;

    public TeacherController(QuizService quizService){
        this.quizService = quizService;
    }

    @GetMapping
    public String dashboard(Model m, Principal p){
        // p.getName() -> email
        m.addAttribute("quizzes", quizService.findAll());
        return "teacher";
    }

    @GetMapping("/teacher_classlist")
    public String classList(Model m){
        m.addAttribute("quizzes", quizService.findAll());
        return "teacher_classlist";
    }

    @GetMapping("/teacher_classlist/edit_quiz/{id}")
    public String editQuiz(@PathVariable Long id, Model m){
        Quiz q = quizService.findById(id).orElse(new Quiz());
        m.addAttribute("quiz", q);
        return "teacher_quizedit";
    }

    @PostMapping("/teacher_classlist/save")
    public String saveQuiz(@ModelAttribute Quiz quiz){
        quizService.save(quiz);
        return "redirect:/teacher/teacher_classlist";
    }
}

