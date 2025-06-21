package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.TopContributorsWiki;
import com.jaspersoft.community.entity.WikiContributorsList;
import com.jaspersoft.community.repository.TopContributorsWikiRepository;
import com.jaspersoft.community.repository.WikiContributorsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/top-contributors-wiki")
public class TopContributorsWikiController {

    @Autowired
    private TopContributorsWikiRepository topContributorsWikiRepository;

    @Autowired
    private WikiContributorsListRepository wikiContributorsListRepository;

    private static final List<String> TEAM_NAMES = Arrays.asList("CS", "Support", "Pre-Sales");
    private static final List<String> MONTHS = Arrays.asList(
    		"JAN24", "FEB24", "MAR24", "APR24", "MAY24", "JUN24", "JUL24", "AUG24", "SEP24", "OCT24", "NOV24", "DEC24",
    		"JAN25", "FEB25", "MAR25", "APR25", "MAY25", "JUN25", "JUL25", "AUG25", "SEP25", "OCT25", "NOV25", "DEC25"
    );

    @GetMapping
    public String getAllRecords(@RequestParam(value = "month", required = false) String month, Model model) {
        List<TopContributorsWiki> records = (month == null || month.isEmpty())
                ? topContributorsWikiRepository.findAll()
                : topContributorsWikiRepository.findByMonth(month);

        model.addAttribute("records", records);
        model.addAttribute("contributors", getUniqueContributors(records));
        model.addAttribute("teams", TEAM_NAMES);
        model.addAttribute("months", MONTHS);
        model.addAttribute("selectedMonth", month);

        return "top-contributors-wiki/list";
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("topContributorsWiki", new TopContributorsWiki());
        model.addAttribute("teams", TEAM_NAMES);
        model.addAttribute("months", MONTHS);
        model.addAttribute("contributors", getContributorNames());
        return "top-contributors-wiki/form";
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute TopContributorsWiki topContributorsWiki) {
        topContributorsWikiRepository.save(topContributorsWiki);
        return "redirect:/top-contributors-wiki"; // Already correct: Spring handles the context path
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        TopContributorsWiki topContributorsWiki = topContributorsWikiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("topContributorsWiki", topContributorsWiki);
        model.addAttribute("teams", TEAM_NAMES);
        model.addAttribute("months", MONTHS);
        model.addAttribute("contributors", getContributorNames());
        return "top-contributors-wiki/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute TopContributorsWiki topContributorsWiki) {
        topContributorsWiki.setId(id);
        topContributorsWikiRepository.save(topContributorsWiki);
        return "redirect:/top-contributors-wiki"; // Already correct: Spring handles the context path
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        topContributorsWikiRepository.deleteById(id);
        return "redirect:/top-contributors-wiki"; // Already correct: Spring handles the context path
    }

    private List<String> getUniqueContributors(List<TopContributorsWiki> records) {
        return records.stream()
                .map(TopContributorsWiki::getContributorName)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> getContributorNames() {
        return wikiContributorsListRepository.findAll()
                .stream()
                .map(WikiContributorsList::getFullName)
                .collect(Collectors.toList());
    }
}