package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.TopContributorsQa;
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

    @GetMapping
    public String getAllRecords(Model model) {
        List<TopContributorsQa> records = topContributorsQaRepository.findAll();
        model.addAttribute("records", records);
        return "top-contributors-qa/list";  // Thymeleaf template
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("topContributorsQa", new TopContributorsQa());
        return "top-contributors-qa/form";  // Thymeleaf template
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute TopContributorsQa topContributorsQa) {
        topContributorsQaRepository.save(topContributorsQa);
        return "redirect:/top-contributors-qa";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        TopContributorsQa topContributorsQa = topContributorsQaRepository.findById(id).orElseThrow();
        model.addAttribute("topContributorsQa", topContributorsQa);
        return "top-contributors-qa/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute TopContributorsQa topContributorsQa) {
        topContributorsQa.setId(id);
        topContributorsQaRepository.save(topContributorsQa);
        return "redirect:/top-contributors-qa";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        topContributorsQaRepository.deleteById(id);
        return "redirect:/top-contributors-qa";
    }
}
