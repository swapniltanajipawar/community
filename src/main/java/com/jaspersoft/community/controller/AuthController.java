package com.jaspersoft.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // returns login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // returns dashboard.html
    }
}
