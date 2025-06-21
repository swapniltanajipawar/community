package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.Qa;
import com.jaspersoft.community.entity.QaContributorsList;
import com.jaspersoft.community.repository.QaRepository;
import com.jaspersoft.community.repository.QaContributorsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/qa")
public class QaController {

    @Autowired
    private QaRepository qaRepository;

    @Autowired
    private QaContributorsListRepository qaContributorsListRepository; // Corrected casing for consistency

    private final List<String> months = List.of("JAN24", "FEB24", "MAR24", "APR24", "MAY24", "JUN24", "JUL24", "AUG24", "SEP24", "OCT24", "NOV24", "DEC24",
    		"JAN25", "FEB25", "MAR25", "APR25", "MAY25", "JUN25", "JUL25", "AUG25", "SEP25", "OCT25", "NOV25", "DEC25");

    // Fetch the list of QA records based on selected month or show all if no month is selected
    @GetMapping
    public String listQa(@RequestParam(required = false) String month, Model model) {
        List<Qa> qaList = (month != null && !month.isEmpty()) ?
                qaRepository.findByMonth(month) : qaRepository.findAll();

        model.addAttribute("qaList", qaList);
        model.addAttribute("month", month);
        model.addAttribute("months", months);
        return "qa/list";
    }

    // Show the form for creating a new QA record
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("qa", new Qa());
        model.addAttribute("contributors", getContributorNames());  // Fetch contributor names dynamically
        model.addAttribute("months", months);
        return "qa/form";
    }

    // Show the form for updating an existing QA record
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Qa qa = qaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Qa ID: " + id));

        model.addAttribute("qa", qa);
        model.addAttribute("contributors", getContributorNames());  // Fetch contributor names dynamically
        model.addAttribute("months", months);
        return "qa/form";
    }

    // Save a new QA record
    @PostMapping
    public String saveQa(@ModelAttribute("qa") Qa qa) {
        qaRepository.save(qa);
        return "redirect:/qa"; // Correct and standard way to redirect
    }

    // Update an existing QA record
    @PostMapping("/update/{id}")
    public String updateQa(@PathVariable Integer id, @ModelAttribute("qa") Qa qa) {
        qa.setId(id);
        qaRepository.save(qa);
        return "redirect:/qa"; // Correct and standard way to redirect
    }

    // Delete a QA record
    @GetMapping("/delete/{id}")
    public String deleteQa(@PathVariable Integer id) {
        qaRepository.deleteById(id);
        return "redirect:/qa"; // Correct and standard way to redirect
    }

    // 🔧 Utility method to get contributor full names dynamically
    private List<String> getContributorNames() {
        return qaContributorsListRepository.findAll()
                .stream()
                .map(QaContributorsList::getFullName)
                .collect(Collectors.toList());
    }
}