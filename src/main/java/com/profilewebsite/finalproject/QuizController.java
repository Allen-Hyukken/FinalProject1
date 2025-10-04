package com.profilewebsite.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {


        @GetMapping("/home")
        public String home() {
            return "student";
        }
        @GetMapping("/logout")
        public String logout() {
            return "redirect:/login";
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }

    @PostMapping("/login")
    public String loginUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
            return "student";
    }

        @GetMapping("/register")
        public String register() {
            return "register";
        }

        @PostMapping("/register")
        public String registerUser(@RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String role,
                                   Model model) {

            // Save user info (you can add your service logic here)
            // userService.save(new User(name, email, password, role));

            // Redirect based on role
            if ("teacher".equalsIgnoreCase(role)) {
                return "redirect:/teacher";  // goes to teachers page
            } else if ("student".equalsIgnoreCase(role)) {
                return "redirect:/student";     // goes to users page
            } else {
                return "redirect:/";         // fallback if no valid role
            }
        }

        @GetMapping("/teacher")
        public String teacherPage() {
            return "teacher"; // returns teacher.html
        }

        @GetMapping("/student")
        public String studentPage() {
            return "student"; // returns user.html
        }



}
