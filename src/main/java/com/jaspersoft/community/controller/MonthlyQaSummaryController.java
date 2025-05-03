package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.MonthlyQaSummary;
import com.jaspersoft.community.repository.MonthlyQaSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/monthly-qa-summary")
public class MonthlyQaSummaryController {

    @Autowired
    private MonthlyQaSummaryRepository monthlyQaSummaryRepository;

    // Show all records (for Thymeleaf)
    @GetMapping
    public String getAllRecords(Model model) {
        List<MonthlyQaSummary> records = monthlyQaSummaryRepository.findAll();
        model.addAttribute("records", records);
        return "monthly-qa-summary/list";  // Thymeleaf template
    }

    // Show form to insert new record
    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("monthlyQaSummary", new MonthlyQaSummary());
        return "monthly-qa-summary/form";  // Thymeleaf template
    }

    // Save new record
    @PostMapping
    public String saveNewRecord(@ModelAttribute MonthlyQaSummary monthlyQaSummary) {
        monthlyQaSummaryRepository.save(monthlyQaSummary);
        return "redirect:/monthly-qa-summary";  // Redirect to the list view
    }

    // Show form to update existing record
    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        MonthlyQaSummary monthlyQaSummary = monthlyQaSummaryRepository.findById(id).orElseThrow();
        model.addAttribute("monthlyQaSummary", monthlyQaSummary);
        return "monthly-qa-summary/form";  // Thymeleaf template
    }

    // Handle update of existing record
    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute MonthlyQaSummary monthlyQaSummary) {
        monthlyQaSummary.setId(id);
        monthlyQaSummaryRepository.save(monthlyQaSummary);
        return "redirect:/monthly-qa-summary";
    }

    // Delete record
    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        monthlyQaSummaryRepository.deleteById(id);
        return "redirect:/monthly-qa-summary";
    }
}
