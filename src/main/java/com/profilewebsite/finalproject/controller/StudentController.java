package com.profilewebsite.finalproject.controller;

import com.profilewebsite.finalproject.model.ClassRoom;
import com.profilewebsite.finalproject.model.User;
import com.profilewebsite.finalproject.service.AttemptService;
import com.profilewebsite.finalproject.service.ClassService;
import com.profilewebsite.finalproject.service.QuizService;
import com.profilewebsite.finalproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final QuizService quizService;
    private final AttemptService attemptService;
    private final UserService userService;
    private final ClassService classService;

    public StudentController(QuizService quizService, AttemptService attemptService, UserService userService,  ClassService classService) {
        this.quizService = quizService;
        this.attemptService = attemptService;
        this.userService = userService;
        this.classService = classService;
    }


    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("classes", classService.getClassesForStudent());
        return "student"; // points to student.html
    }

    @PostMapping("/join")
    public String joinClass(@RequestParam String code, RedirectAttributes redirectAttributes) {
        boolean joined = classService.joinClassByCode(code);
        if (joined) {
            redirectAttributes.addAttribute("success", true);
        } else {
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/student";
    }

    @GetMapping("/class/{classId}/quiz/{quizId}")
    public String takeQuiz(@PathVariable Long classId, @PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", quizService.getQuizById(quizId));
        // You should also add classId to the model if needed on the quiz page
        model.addAttribute("classId", classId);
        return "studentquiz"; // your quiz-taking page
    }

    @GetMapping("/student_classlist/{classId}")
    public String viewClassQuizzes(@PathVariable Long classId, Model model) {
        ClassRoom classRoom = classService.findById(classId).orElseThrow();
        model.addAttribute("classRoom", classRoom);
        model.addAttribute("quizzes", quizService.findByClassRoomId(classId));
        return "student_classlist";
    }

    @PostMapping("/student_class/{classId}/quiz/{quizId}/submit")
    public String submitQuiz(@PathVariable Long classId,
                             @PathVariable Long quizId,
                             @RequestParam Map<String,String> params,
                             Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        attemptService.submitAnswers(quizId, user.getId(), params);

        // Redirect back to the class page
        return "redirect:/student/student_classlist/" + classId;
    }
}