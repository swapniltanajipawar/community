package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.MonthlyQaSummary;
import com.jaspersoft.community.repository.MonthlyQaSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// import jakarta.servlet.http.HttpServletRequest; // You can remove this import if request is only used for getContextPath()

import java.util.List;

@Controller
@RequestMapping("/monthly-qa-summary")
public class MonthlyQaSummaryController {

    @Autowired
    private MonthlyQaSummaryRepository monthlyQaSummaryRepository;

    // You can remove the HttpServletRequest injection if it's only used for getContextPath() in redirects.
    // private final HttpServletRequest request;
    // @Autowired
    // public MonthlyQaSummaryController(HttpServletRequest request) {
    //     this.request = request;
    // }

    private final List<String> months = List.of("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                                                 "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    @GetMapping
    public String getAllRecords(@RequestParam(value = "month", required = false) String month, Model model) {
        model.addAttribute("months", months);
        model.addAttribute("selectedMonth", month);

        List<MonthlyQaSummary> records;
        if (month != null && !month.isEmpty()) {
            records = monthlyQaSummaryRepository.findByMonth(month.toUpperCase());
        } else {
            records = monthlyQaSummaryRepository.findAll();
        }
        model.addAttribute("records", records);
        return "monthly-qa-summary/list";
    }

    @GetMapping("/new")
    public String showFormForNewRecord(Model model) {
        model.addAttribute("months", months);
        model.addAttribute("monthlyQaSummary", new MonthlyQaSummary());
        return "monthly-qa-summary/form";
    }

    @PostMapping
    public String saveNewRecord(@ModelAttribute MonthlyQaSummary monthlyQaSummary) {
        monthlyQaSummaryRepository.save(monthlyQaSummary);
        // CORRECTED: Use standard Spring redirect. Spring handles the context path.
        return "redirect:/monthly-qa-summary";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable Integer id, Model model) {
        MonthlyQaSummary monthlyQaSummary = monthlyQaSummaryRepository.findById(id).orElseThrow();
        model.addAttribute("months", months);
        model.addAttribute("monthlyQaSummary", monthlyQaSummary);
        return "monthly-qa-summary/form";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Integer id, @ModelAttribute MonthlyQaSummary monthlyQaSummary) {
        monthlyQaSummary.setId(id);
        monthlyQaSummaryRepository.save(monthlyQaSummary);
        // CORRECTED: Use standard Spring redirect. Spring handles the context path.
        return "redirect:/monthly-qa-summary";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id) {
        monthlyQaSummaryRepository.deleteById(id);
        // CORRECTED: Use standard Spring redirect. Spring handles the context path.
        return "redirect:/monthly-qa-summary";
    }
}