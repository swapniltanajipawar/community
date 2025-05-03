package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.MonthlyWikiSummary;
import com.jaspersoft.community.repository.MonthlyWikiSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/monthly-wiki-summary")
public class MonthlyWikiSummaryController {

    @Autowired
    private MonthlyWikiSummaryRepository monthlyWikiSummaryRepository;

    @GetMapping
    public String getAllRecords(Model model) {
        List<MonthlyWikiSummary> records = monthlyWikiSummaryRepository.findAll();
        model.addAttribute("records", records);
        return "monthly-wiki-summary/list";  // Thymeleaf template
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("monthlyWikiSummary", new MonthlyWikiSummary());
        return "monthly-wiki-summary/form";  // Thymeleaf template
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute MonthlyWikiSummary monthlyWikiSummary) {
        monthlyWikiSummaryRepository.save(monthlyWikiSummary);
        return "redirect:/monthly-wiki-summary";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        MonthlyWikiSummary monthlyWikiSummary = monthlyWikiSummaryRepository.findById(id).orElseThrow();
        model.addAttribute("monthlyWikiSummary", monthlyWikiSummary);
        return "monthly-wiki-summary/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute MonthlyWikiSummary monthlyWikiSummary) {
        monthlyWikiSummary.setId(id);
        monthlyWikiSummaryRepository.save(monthlyWikiSummary);
        return "redirect:/monthly-wiki-summary";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        monthlyWikiSummaryRepository.deleteById(id);
        return "redirect:/monthly-wiki-summary";
    }
}
