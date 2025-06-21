package com.jaspersoft.community.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jaspersoft.community.entity.Role;
import com.jaspersoft.community.entity.User;
import com.jaspersoft.community.repository.RoleRepository;
import com.jaspersoft.community.repository.UserRepository;

//UserManagementController.java


@Controller
@RequestMapping("/manage")
public class UserManagementController {
 @Autowired private UserRepository userRepo;
 @Autowired private RoleRepository roleRepo;
 
 @GetMapping
 public String redirectToUsers() {
     return "redirect:/manage/users";
 }

 @GetMapping("/users")
 public String listUsers(Model model) {
     model.addAttribute("users", userRepo.findAll());
     return "manage/user-list";
 }

 @GetMapping("/edit/{id}")
 public String editUser(@PathVariable Long id, Model model) {
     User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     List<Role> allRoles = roleRepo.findAll();
     model.addAttribute("user", user);
     model.addAttribute("allRoles", allRoles);
     return "manage/edit-user";
 }

 @PostMapping("/edit")
 public String updateUser(@RequestParam Long id, @RequestParam List<Long> roleIds) {
     User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     Set<Role> newRoles = new HashSet<>(roleRepo.findAllById(roleIds));
     user.setRoles(newRoles);
     userRepo.save(user);
     return "redirect:/manage/users";
 }


 @PostMapping("/delete/{id}")
 public String deleteUser(@PathVariable Long id) {
     userRepo.deleteById(id);
     return "redirect:/manage/users";
 }
}