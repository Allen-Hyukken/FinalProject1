package com.profilewebsite.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {

        @GetMapping("/index")
        public String index() {
            return "index";
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/about")
        public String about() {
            return "about";
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
