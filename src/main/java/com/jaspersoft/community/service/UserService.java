package com.jaspersoft.community.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaspersoft.community.entity.PasswordResetToken;
import com.jaspersoft.community.entity.Role;
import com.jaspersoft.community.entity.User;
import com.jaspersoft.community.entity.UsernameResetToken;
import com.jaspersoft.community.repository.PasswordResetTokenRepository;
import com.jaspersoft.community.repository.RoleRepository;
import com.jaspersoft.community.repository.UserRepository;
import com.jaspersoft.community.repository.UsernameResetTokenRepository;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PasswordResetTokenRepository tokenRepo;
    @Autowired private UsernameResetTokenRepository usernameTokenRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public User registerNewUser(User userForm) {
        if (userRepo.findByUsername(userForm.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (!userForm.getUsername().matches("^[a-zA-Z0-9]+$")) {
            throw new RuntimeException("Username must not contain spaces or special characters");
        }

        if (userRepo.findByEmail(userForm.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        validatePassword(userForm.getPassword());
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));

        Role userRole = roleRepo.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = roleRepo.save(new Role("ROLE_USER"));
        }

        userForm.getRoles().add(userRole);
        return userRepo.save(userForm);
    }

    public void updatePasswordByEmail(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        validatePassword(newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Transactional
    public String createPasswordResetToken(User user) {
        tokenRepo.findByUser(user).ifPresent(existing -> {
            tokenRepo.delete(existing);
            tokenRepo.flush(); // 💡 Ensures it's deleted before new one is inserted
        });

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepo.save(resetToken);
        return token;
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepo.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        validatePassword(newPassword);

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        tokenRepo.delete(resetToken);
        tokenRepo.flush(); // 💡 Ensure deletion is flushed immediately
    }

    @Transactional
    public String createUsernameResetToken(User user) {
        usernameTokenRepo.findByUser(user).ifPresent(existing -> {
            usernameTokenRepo.delete(existing);
            usernameTokenRepo.flush(); // 💡 Same logic for username tokens
        });

        String token = UUID.randomUUID().toString();
        UsernameResetToken resetToken = new UsernameResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        usernameTokenRepo.save(resetToken);
        return token;
    }

    @Transactional
    public void resetUsername(String token, String newUsername) {
        UsernameResetToken resetToken = usernameTokenRepo.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        if (userRepo.findByUsername(newUsername).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (!newUsername.matches("^[a-zA-Z0-9]+$")) {
            throw new RuntimeException("Username must be alphanumeric without spaces or special characters");
        }

        User user = resetToken.getUser();
        user.setUsername(newUsername);
        userRepo.save(user);

        usernameTokenRepo.delete(resetToken);
        usernameTokenRepo.flush(); // 💡 Flush after deletion
    }

    private void validatePassword(String password) {
        if (password.length() < 5 || !password.contains("@") || password.contains(" ")) {
            throw new RuntimeException("Password must be at least 5 characters, include '@', and contain no spaces");
        }
    }
}
