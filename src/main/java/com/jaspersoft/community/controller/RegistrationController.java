package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.User;
import com.jaspersoft.community.repository.UserRepository;
import com.jaspersoft.community.service.EmailService;
import com.jaspersoft.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;
    @Autowired private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userForm") User userForm,
                               @RequestParam String confirmPassword,
                               Model model) {
        try {
            if (!userForm.getPassword().equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            userService.registerNewUser(userForm);
            return "redirect:/login";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String token = userService.createPasswordResetToken(user.get());
            emailService.sendResetPasswordLink(email, token);
            model.addAttribute("message", "Password reset link sent to your email.");
        } else {
            model.addAttribute("error", "Email not registered.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            userService.resetPassword(token, newPassword);
            return "redirect:/login?resetSuccess";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("token", token);
            return "reset-password";
        }
    }

    @GetMapping("/forgot-username")
    public String forgotUsernamePage() {
        return "forgot-username";
    }

    @PostMapping("/forgot-username")
    public String processForgotUsername(@RequestParam String email, Model model) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String token = userService.createUsernameResetToken(user.get());
            emailService.sendResetUsernameLink(email, token);
            model.addAttribute("message", "Username reset link sent to your email.");
        } else {
            model.addAttribute("error", "Email not registered.");
        }
        return "forgot-username";
    }

    @GetMapping("/reset-username")
    public String resetUsernameForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-username";
    }

    @PostMapping("/reset-username")
    public String resetUsername(@RequestParam String token,
                                @RequestParam String newUsername,
                                Model model) {
        try {
            userService.resetUsername(token, newUsername);
            return "redirect:/login?usernameResetSuccess";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("token", token);
            return "reset-username";
        }
    }
}
