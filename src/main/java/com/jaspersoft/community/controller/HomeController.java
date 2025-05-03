package com.jaspersoft.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model) {
        // If you need data to display, add it to the model
        model.addAttribute("message", "Welcome to the Home page");
        return "home";  // This corresponds to home.html (Thymeleaf template)
    }
}
