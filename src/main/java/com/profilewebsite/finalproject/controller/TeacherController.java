package com.profilewebsite.finalproject.controller;

import com.profilewebsite.finalproject.model.*;
import com.profilewebsite.finalproject.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final ClassService classService;
    private final UserService userService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final AttemptService attemptService;

    public TeacherController(ClassService classService, UserService userService, QuizService quizService,
                             QuestionService questionService, AttemptService attemptService) {
        this.classService = classService;
        this.userService = userService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.attemptService = attemptService;
    }

    @GetMapping
    public String dashboard(Model model, Principal principal) {
        User teacher = userService.findByEmail(principal.getName()).orElseThrow();
        List<ClassRoom> classes = classService.findByTeacherId(teacher.getId());
        model.addAttribute("classes", classes);
        return "teacher";
    }

    @PostMapping("/create")
    public String createClass(@RequestParam String name,
                              @RequestParam(required = false) MultipartFile banner,
                              Principal principal) throws IOException {
        var teacher = userService.findByEmail(principal.getName()).orElseThrow();
        classService.createClass(name, banner, teacher);
        return "redirect:/teacher";
    }

    /**
     * CHANGED: Merged duplicate @GetMapping("/class/{id}") methods.
     * This single method now handles viewing a class, its students, and its quizzes.
     */
    @GetMapping("/class/{id}")
    public String viewClass(@PathVariable Long id, Model model) {
        ClassRoom classRoom = classService.findById(id).orElseThrow();
        List<Quiz> quizzes = quizService.findByClassRoomId(id);
        List<User> students = userService.findStudentsByClassRoom(id);

        model.addAttribute("classRoom", classRoom);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("students", students);

        return "teacher_classlist";
    }

    // Show quiz creation page
    @GetMapping("/teacher_classlist/create_quiz")
    public String showCreateQuizPage(Model model) {
        // You may want to pass the classId here so you know which class to add the quiz to
        // For now, it just loads all classes for a dropdown
        model.addAttribute("classes", classService.findAll());
        return "create_quiz"; // Thymeleaf template
    }

    // Save quiz (NEW quiz)
    @PostMapping("/save_quiz")
    public String saveQuiz(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam Long classId) {
        ClassRoom classRoom = classService.findById(classId).orElse(null);
        if (classRoom == null) {
            // Handle error: class not found
            return "redirect:/teacher?error=classNotFound";
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setClassRoom(classRoom);
        quizService.save(quiz);
        // Redirect back to the class page to see the new quiz
        return "redirect:/teacher/class/" + classId;
    }

    @PostMapping("/add_question")
    public String addQuestion(@RequestParam Long quizId,
                              @RequestParam String type,
                              @RequestParam String text,
                              @RequestParam(required = false) String choice1,
                              @RequestParam(required = false) String choice2,
                              @RequestParam(required = false) String choice3,
                              @RequestParam(required = false) String choice4,
                              @RequestParam(required = false) String correct,
                              @RequestParam(required = false) String guideAnswer) {

        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz == null) {
            return "redirect:/teacher?error=quizNotFound";
        }

        Question question = new Question();
        question.setQuiz(quiz);
        question.setType(type);
        question.setText(text);
        question.setCorrectAnswer(correct);
        question.setGuideAnswer(guideAnswer);

        // Only add choices if MCQ
        if ("MCQ".equalsIgnoreCase(type)) {
            List<Choice> choices = new ArrayList<>();
            if (choice1 != null && !choice1.isEmpty()) choices.add(new Choice(choice1, question));
            if (choice2 != null && !choice2.isEmpty()) choices.add(new Choice(choice2, question));
            if (choice3 != null && !choice3.isEmpty()) choices.add(new Choice(choice3, question));
            if (choice4 != null && !choice4.isEmpty()) choices.add(new Choice(choice4, question));
            question.setChoices(choices);
        }

        questionService.save(question);

        return "redirect:/teacher/teacher_classlist/edit_quiz/" + quizId;
    }


    @GetMapping("/teacher_classlist/results/{quizId}")
    public String showQuizResults(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        List<Attempt> attempts = attemptService.findByQuiz(quizId);
        double avg = attempts.isEmpty() ? 0 :
                attempts.stream().mapToDouble(Attempt::getScore).average().orElse(0);

        model.addAttribute("quiz", quiz);
        model.addAttribute("attempts", attempts);
        model.addAttribute("averageScore", avg);
        return "teacher_insidequiz_result";
    }

    @GetMapping("/teacher_classlist/edit_quiz/{quizId}")
    public String editQuiz(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        model.addAttribute("quiz", quiz);
        return "teacher_quizedit";
    }

    /**
     * CHANGED: Renamed method from saveQuiz to updateQuiz for clarity.
     * CHANGED: Updated mapping from "/save" to "/update".
     * CHANGED: Fixed redirect to go back to the class page.
     * This assumes the form in 'teacher_quizedit' posts the full Quiz object,
     * including its classRoom field with at least classRoom.id populated.
     */
    @PostMapping("/teacher_classlist/update")
    public String updateQuiz(@ModelAttribute Quiz quiz) {
        // Ensure the classRoom object is attached if only the ID was submitted
        if (quiz.getClassRoom() != null && quiz.getClassRoom().getId() != null) {
            ClassRoom classRoom = classService.findById(quiz.getClassRoom().getId()).orElse(null);
            quiz.setClassRoom(classRoom);
        } else {
            // Handle error: Quiz must have a class
            // You might need to add a hidden input for classRoom.id in your form
            return "redirect:/teacher?error=classMissing";
        }

        quizService.save(quiz); // Saves (updates) the existing quiz

        // Redirect back to the class list for that specific class
        return "redirect:/teacher/class/" + quiz.getClassRoom().getId();
    }
}