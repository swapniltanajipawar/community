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

    private final List<String> months = List.of("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    @GetMapping
    public String getAllRecords(@RequestParam(value = "month", required = false) String month, Model model) {
        model.addAttribute("months", months);
        model.addAttribute("selectedMonth", month);

        List<MonthlyWikiSummary> records;
        if (month != null && !month.isEmpty()) {
            records = monthlyWikiSummaryRepository.findByMonth(month.toUpperCase());
        } else {
            records = monthlyWikiSummaryRepository.findAll();
        }
        model.addAttribute("records", records);
        return "monthly-wiki-summary/list";
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("months", months);
        model.addAttribute("monthlyWikiSummary", new MonthlyWikiSummary());
        return "monthly-wiki-summary/form";
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute MonthlyWikiSummary monthlyWikiSummary) {
        monthlyWikiSummaryRepository.save(monthlyWikiSummary);
        // Corrected: Using standard Spring redirect. Spring handles the context path automatically.
        return "redirect:/monthly-wiki-summary";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        MonthlyWikiSummary monthlyWikiSummary = monthlyWikiSummaryRepository.findById(id).orElseThrow();
        model.addAttribute("months", months);
        model.addAttribute("monthlyWikiSummary", monthlyWikiSummary);
        return "monthly-wiki-summary/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute MonthlyWikiSummary monthlyWikiSummary) {
        monthlyWikiSummary.setId(id);
        monthlyWikiSummaryRepository.save(monthlyWikiSummary);
        // Corrected: Using standard Spring redirect. Spring handles the context path automatically.
        return "redirect:/monthly-wiki-summary";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        monthlyWikiSummaryRepository.deleteById(id);
        // Corrected: Using standard Spring redirect. Spring handles the context path automatically.
        return "redirect:/monthly-wiki-summary";
    }
}