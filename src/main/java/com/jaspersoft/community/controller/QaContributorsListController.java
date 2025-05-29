package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.QaContributorsList;
import com.jaspersoft.community.repository.QaContributorsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/qa-contributors")
public class QaContributorsListController {

    @Autowired
    private QaContributorsListRepository repository;

    private List<String> getCategoryOptions() {
        return Arrays.asList("CS", "Support", "Pre-Sales");
    }

    @GetMapping
    public String listQaContributors(Model model) {
        model.addAttribute("contributorsList", repository.findAll());
        return "qa_contributors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("qaContributorsList", new QaContributorsList());
        model.addAttribute("categories", getCategoryOptions());
        return "qa_contributors/form";
    }

    @PostMapping
    public String saveQaContributor(@ModelAttribute("qaContributorsList") QaContributorsList qaContributorsList) {
        repository.save(qaContributorsList);
        return "redirect:/qa-contributors"; // Already correct: Spring handles the context path
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        QaContributorsList qaContributorsList = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contributor ID: " + id));
        model.addAttribute("qaContributorsList", qaContributorsList);
        model.addAttribute("categories", getCategoryOptions());
        return "qa_contributors/form";
    }

    @PostMapping("/update/{id}")
    public String updateQaContributor(@PathVariable Long id, @ModelAttribute("qaContributorsList") QaContributorsList qaContributorsList) {
        qaContributorsList.setId(id);
        repository.save(qaContributorsList);
        return "redirect:/qa-contributors"; // Already correct: Spring handles the context path
    }

    @GetMapping("/delete/{id}")
    public String deleteQaContributor(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/qa-contributors"; // Already correct: Spring handles the context path
    }
}