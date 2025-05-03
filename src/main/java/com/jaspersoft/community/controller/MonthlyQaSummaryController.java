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

    private final List<String> months = List.of("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                                                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    // Show all records or filter by month
    @GetMapping
    public String getAllRecords(@RequestParam(value = "month", required = false) String month, Model model) {
        model.addAttribute("months", months);
        model.addAttribute("selectedMonth", month);

        List<MonthlyQaSummary> records;
        if (month != null && !month.isEmpty()) {
            records = monthlyQaSummaryRepository.findByMonth(month.toUpperCase());
        } else {
            records = monthlyQaSummaryRepository.findAll();
        }
        model.addAttribute("records", records);
        return "monthly-qa-summary/list";
    }

    // Show form to insert new record
    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("months", months);
        model.addAttribute("monthlyQaSummary", new MonthlyQaSummary());
        return "monthly-qa-summary/form";
    }

    // Save new record
    @PostMapping
    public String saveNewRecord(@ModelAttribute MonthlyQaSummary monthlyQaSummary) {
        monthlyQaSummaryRepository.save(monthlyQaSummary);
        return "redirect:/monthly-qa-summary";
    }

    // Show form to update existing record
    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        MonthlyQaSummary monthlyQaSummary = monthlyQaSummaryRepository.findById(id).orElseThrow();
        model.addAttribute("months", months);
        model.addAttribute("monthlyQaSummary", monthlyQaSummary);
        return "monthly-qa-summary/form";
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