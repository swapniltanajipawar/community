package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.TopContributorsQa;
import com.jaspersoft.community.repository.QaContributorsListRepository;
import com.jaspersoft.community.repository.TopContributorsQaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/top-contributors-qa")
public class TopContributorsQaController {

    @Autowired
    private TopContributorsQaRepository topContributorsQaRepository;

    @Autowired
    private QaContributorsListRepository qaContributorListRepository;

    private static final List<String> MONTHS = List.of("JAN24", "FEB24", "MAR24", "APR24", "MAY24", "JUN24", "JUL24", "AUG24", "SEP24", "OCT24", "NOV24", "DEC24",
    		"JAN25", "FEB25", "MAR25", "APR25", "MAY25", "JUN25", "JUL25", "AUG25", "SEP25", "OCT25", "NOV25", "DEC25");
    private static final List<String> TEAMS = List.of("CS", "Support", "Pre-Sales");

    @GetMapping
    public String getAllRecords(@RequestParam(required = false) String month, Model model) {
        List<TopContributorsQa> records;
        if (month != null && !month.isEmpty()) {
            records = topContributorsQaRepository.findByMonth(month);
            model.addAttribute("selectedMonth", month); // Send selected month to the view
        } else {
            records = topContributorsQaRepository.findAll();
            model.addAttribute("selectedMonth", null); // No month selected
        }
        model.addAttribute("records", records);
        model.addAttribute("months", MONTHS);
        model.addAttribute("teams", TEAMS);
        model.addAttribute("contributors", qaContributorListRepository.findAll());
        return "top-contributors-qa/list";
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("topContributorsQa", new TopContributorsQa());
        model.addAttribute("months", MONTHS);
        model.addAttribute("teams", TEAMS);
        model.addAttribute("contributors", qaContributorListRepository.findAll());
        return "top-contributors-qa/form";
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute TopContributorsQa topContributorsQa) {
        topContributorsQaRepository.save(topContributorsQa);
        return "redirect:/top-contributors-qa"; // Correct and standard way to redirect
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        TopContributorsQa topContributorsQa = topContributorsQaRepository.findById(id).orElseThrow();
        model.addAttribute("topContributorsQa", topContributorsQa);
        model.addAttribute("months", MONTHS);
        model.addAttribute("teams", TEAMS);
        model.addAttribute("contributors", qaContributorListRepository.findAll());
        return "top-contributors-qa/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute TopContributorsQa topContributorsQa) {
        topContributorsQa.setId(id);
        topContributorsQaRepository.save(topContributorsQa);
        return "redirect:/top-contributors-qa"; // Correct and standard way to redirect
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        topContributorsQaRepository.deleteById(id);
        return "redirect:/top-contributors-qa"; // Correct and standard way to redirect
    }
}