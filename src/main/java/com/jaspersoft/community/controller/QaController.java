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

@Controller
@RequestMapping("/qa")
public class QaController {

    @Autowired
    private QaRepository qaRepository;

    @Autowired
    private QaContributorsListRepository contributorsListRepository;

    // ✅ Modified method to support optional month filtering
    @GetMapping
    public String listQa(@RequestParam(required = false) Integer month, Model model) {
        List<Qa> qaList;

        if (month != null) {
            qaList = qaRepository.findByMonth(month); // This method must be present in QaRepository
        } else {
            qaList = qaRepository.findAll();
        }

        model.addAttribute("qaList", qaList);
        model.addAttribute("month", month); // 🔁 Send selected month back to view

        return "qa/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("qa", new Qa());

        List<QaContributorsList> contributors = contributorsListRepository.findAll();
        model.addAttribute("contributors", contributors);

        return "qa/form";
    }

    @PostMapping
    public String saveQa(@ModelAttribute("qa") Qa qa) {
        qaRepository.save(qa);
        return "redirect:/qa";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Qa qa = qaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid QA ID: " + id));
        model.addAttribute("qa", qa);

        List<QaContributorsList> contributors = contributorsListRepository.findAll();
        model.addAttribute("contributors", contributors);

        return "qa/form";
    }

    @PostMapping("/update/{id}")
    public String updateQa(@PathVariable Integer id, @ModelAttribute("qa") Qa qa) {
        qa.setId(id);
        qaRepository.save(qa);
        return "redirect:/qa";
    }

    @GetMapping("/delete/{id}")
    public String deleteQa(@PathVariable Integer id) {
        qaRepository.deleteById(id);
        return "redirect:/qa";
    }
}
