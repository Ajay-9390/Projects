package com.anr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.anr.model.Worker;
import com.anr.service.WorkerService;

@Controller
@RequestMapping("/shop")
public class WorkerController {

	@Autowired
	private WorkerService workerService;

	// Adding new worker
	// Admin-specific endpoint to show the form for adding a new worker
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/worker/add")
	public String showAddWorkerForm(Model model) {
		model.addAttribute("worker", new Worker());
		return "workerForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/add")
	public String addWorker(@Validated Worker worker, BindingResult result) {
		if (result.hasErrors()) {
			return "workerForm";
		}

		// Calculate the salary based on attended days and salary per day
		double totalSalary = worker.getSalaryPerDay() * worker.getAttendedDays();
		worker.setTotalSalary(totalSalary);
		workerService.addWorker(worker);
		return "redirect:/shop/workers";
	}

	// edit existing worker
	// Admin-specific endpoint to show the form for editing an existing worker
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/worker/edit/{id}")
	public String showEditWorkerForm(@PathVariable Long id, Model model) {
		Worker worker = workerService.getWorkerById(id); // This could be null or an Optional

		if (worker == null) {
			// If worker is null, redirect with error message
			model.addAttribute("errorMessage",
					"Worker not found with ID " + id + ". Please try again with a valid ID.");
			return "workersList"; // Assuming this is the workers list page
		}

		model.addAttribute("worker", worker);
		return "workerForm"; // This is the form for editing the worker
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/edit")
	public String updateWorker(@RequestParam Long id, @Validated Worker workerDetails, BindingResult result) {
		if (result.hasErrors()) {
			return "workerForm";
		}

		// Calculate the new total salary based on attended days and salary per day
		double totalSalary = workerDetails.getSalaryPerDay() * workerDetails.getAttendedDays();
		workerDetails.setSalaryPerDay(totalSalary);
		workerService.updateWorker(id, workerDetails);
		return "redirect:/shop/workers";
	}

	// delete worker
	// Admin-specific endpoint to delete a worker
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/delete/{id}")
	public String deleteWorkerByPost(@PathVariable Long id, Model model) {
		Worker worker = workerService.getWorkerById(id);
		if (worker == null) {
			model.addAttribute("errorMessage",
					"Worker not found with ID " + id + ". Please try again with a valid ID.");
			return "workersList";
		} else {
			workerService.deleteWorker(id);
		}
		return "redirect:/shop/workers"; // Redirect to the workers list page after success
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/worker/delete/{id}")
	public String deleteWorkerByGet(@PathVariable Long id, Model model) {
		Worker worker = workerService.getWorkerById(id);
		if (worker == null) {
			model.addAttribute("errorMessage",
					"Worker not found with ID " + id + ". Please try again with a valid ID.");
			return "workersList";
		} else {
			workerService.deleteWorker(id);
		}
		return "redirect:/shop/workers"; // Redirect to the workers list page after success
	}

	// display workers
	// Worker and Admin-specific endpoint to view all workers
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/workers")
	public String viewWorkers(Model model) {

		// Get the current authenticated user's authorities
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Worker> workers = workerService.getAllWorkers();
		model.addAttribute("workers", workers);
		String viewName = null;

		// Check if the user has the ROLE_ADMIN authority
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if ("ROLE_ADMIN".equals(authority.getAuthority())) {
				viewName = "workersList";
			} else {
				viewName = "workersList2";
			}
		}
		return viewName;

	}

}
