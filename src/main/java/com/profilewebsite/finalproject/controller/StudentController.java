package com.profilewebsite.finalproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.profilewebsite.finalproject.service.QuizService;
import com.profilewebsite.finalproject.service.AttemptService;
import com.profilewebsite.finalproject.model.Quiz;
import java.util.Map;
import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final QuizService quizService;
    private final AttemptService attemptService;
    private final com.profilewebsite.finalproject.service.UserService userService;

    public StudentController(QuizService quizService, AttemptService attemptService, com.profilewebsite.finalproject.service.UserService userService){
        this.quizService = quizService;
        this.attemptService = attemptService;
        this.userService = userService;
    }

    @GetMapping("/student_class")
    public String classPage(Model m){
        m.addAttribute("quizzes", quizService.findAll());
        return "student_class";
    }

    @GetMapping("/student_class/{classId}/quiz/{quizId}")
    public String takeQuiz(@PathVariable Long classId, @PathVariable Long quizId, Model model){
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        model.addAttribute("quiz", quiz);
        return "studentquiz";
    }

    @PostMapping("/student_class/{classId}/quiz/{quizId}/submit")
    public String submitQuiz(@PathVariable Long classId, @PathVariable Long quizId,
                             @RequestParam Map<String,String> params, Principal principal){
        // find current student
        com.profilewebsite.finalproject.model.User user = userService.findByEmail(principal.getName()).orElseThrow();
        attemptService.submitAnswers(quizId, user.getId(), params);
        return "redirect:/student/student_class";
    }
}
