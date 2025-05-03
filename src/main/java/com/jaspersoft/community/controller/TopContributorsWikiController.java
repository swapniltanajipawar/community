package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.TopContributorsWiki;
import com.jaspersoft.community.repository.TopContributorsWikiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/top-contributors-wiki")
public class TopContributorsWikiController {

    @Autowired
    private TopContributorsWikiRepository topContributorsWikiRepository;

    @GetMapping
    public String getAllRecords(Model model) {
        List<TopContributorsWiki> records = topContributorsWikiRepository.findAll();
        model.addAttribute("records", records);
        return "top-contributors-wiki/list";  // Thymeleaf template
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("topContributorsWiki", new TopContributorsWiki());
        return "top-contributors-wiki/form";  // Thymeleaf template
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute TopContributorsWiki topContributorsWiki) {
        topContributorsWikiRepository.save(topContributorsWiki);
        return "redirect:/top-contributors-wiki";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        TopContributorsWiki topContributorsWiki = topContributorsWikiRepository.findById(id).orElseThrow();
        model.addAttribute("topContributorsWiki", topContributorsWiki);
        return "top-contributors-wiki/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute TopContributorsWiki topContributorsWiki) {
        topContributorsWiki.setId(id);
        topContributorsWikiRepository.save(topContributorsWiki);
        return "redirect:/top-contributors-wiki";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        topContributorsWikiRepository.deleteById(id);
        return "redirect:/top-contributors-wiki";
    }
}
