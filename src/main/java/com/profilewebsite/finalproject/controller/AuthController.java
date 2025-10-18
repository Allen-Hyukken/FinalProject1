package com.profilewebsite.finalproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.profilewebsite.finalproject.model.User;
import com.profilewebsite.finalproject.service.UserService;

@Controller
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService){ this.userService = userService; }

    @GetMapping("/register")
    public String showRegisterForm(Model m){
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        // default to STUDENT if blank
        if(user.getRole() == null) user.setRole("STUDENT");
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

