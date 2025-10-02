package com.profilewebsite.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {

        @GetMapping("/home")
        public String home() {
            return "home";
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/home/quizzes")
        public String quizzes() {
            return "quizzes";
        }

        @GetMapping("/landing")
        public String landing() {
            return "landing-single";
        }

        @GetMapping("/courses")
        public String courses() {
            return "courses";
        }
}
