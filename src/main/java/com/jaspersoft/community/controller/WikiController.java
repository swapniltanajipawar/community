package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.Wiki;
import com.jaspersoft.community.entity.WikiContributorsList;
import com.jaspersoft.community.repository.WikiRepository;
import com.jaspersoft.community.repository.WikiContributorsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wiki")
public class WikiController {

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private WikiContributorsListRepository wikiContributorsListRepository;

    private final List<String> months = List.of("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                                                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    @GetMapping
    public String listWiki(@RequestParam(required = false) String month, Model model) {
        List<Wiki> wikiList = (month != null && !month.isEmpty())
                ? wikiRepository.findByMonth(month)
                : wikiRepository.findAll();

        model.addAttribute("wikiList", wikiList);
        model.addAttribute("month", month);
        model.addAttribute("months", months);
        return "wiki/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("wiki", new Wiki());
        model.addAttribute("months", months);
        model.addAttribute("contributors", getContributorNames());
        return "wiki/form";
    }

    @PostMapping
    public String saveWiki(@ModelAttribute("wiki") Wiki wiki) {
        wikiRepository.save(wiki);
        return "redirect:/wiki";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Wiki wiki = wikiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Wiki ID: " + id));
        model.addAttribute("wiki", wiki);
        model.addAttribute("months", months);
        model.addAttribute("contributors", getContributorNames());
        return "wiki/form";
    }

    @PostMapping("/update/{id}")
    public String updateWiki(@PathVariable Integer id, @ModelAttribute("wiki") Wiki wiki) {
        wiki.setId(id);
        wikiRepository.save(wiki);
        return "redirect:/wiki";
    }

    @GetMapping("/delete/{id}")
    public String deleteWiki(@PathVariable Integer id) {
        wikiRepository.deleteById(id);
        return "redirect:/wiki";
    }

    // 🔧 Utility method to get contributor full names
    private List<String> getContributorNames() {
        return wikiContributorsListRepository.findAll()
                .stream()
                .map(WikiContributorsList::getFullName)
                .collect(Collectors.toList());
    }
}
