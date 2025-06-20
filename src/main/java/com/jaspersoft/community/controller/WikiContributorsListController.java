package com.jaspersoft.community.controller;

import com.jaspersoft.community.entity.WikiContributorsList;
import com.jaspersoft.community.repository.WikiContributorsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/wiki-contributors")
public class WikiContributorsListController {

	@Autowired
	private WikiContributorsListRepository repository;

	private List<String> getCategoryOptions() {
		return Arrays.asList("CS", "Support", "Pre-Sales");
	}

	// List all contributors
	@GetMapping
	public String listContributors(Model model) {
		model.addAttribute("contributors", repository.findAll());
		return "wiki_contributors/list";
	}

	// Show form to add new contributor
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("contributor", new WikiContributorsList());
		model.addAttribute("categories", getCategoryOptions()); // Add categories for dropdown
		return "wiki_contributors/form";
	}

	// Save new contributor
	@PostMapping
	public String saveContributor(@ModelAttribute("contributor") WikiContributorsList contributor) {
		repository.save(contributor);
		return "redirect:/wiki-contributors"; // Already correct: Spring handles the context path
	}

	// Show form to update contributor
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable Long id, Model model) {
		WikiContributorsList contributor = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid contributor ID: " + id));
		model.addAttribute("contributor", contributor);
		model.addAttribute("categories", getCategoryOptions()); // Add categories for dropdown
		return "wiki_contributors/form";
	}

	// Update contributor
	@PostMapping("/update/{id}")
	public String updateContributor(@PathVariable Long id,
			@ModelAttribute("contributor") WikiContributorsList contributor) {
		contributor.setId(id);
		repository.save(contributor);
		return "redirect:/wiki-contributors"; // Already correct: Spring handles the context path
	}

	// Delete contributor
	@GetMapping("/delete/{id}")
	public String deleteContributor(@PathVariable Long id) {
		repository.deleteById(id);
		return "redirect:/wiki-contributors"; // Already correct: Spring handles the context path
	}
}