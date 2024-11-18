package com.anr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

	// Admin-specific endpoint to show the form for adding a new worker
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/worker/add")
	public String showAddWorkerForm(Model model) {
		model.addAttribute("worker", new Worker()); // Provide an empty Worker object for the form
		return "workerForm"; // Thymeleaf template for adding worker
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/add")
	public String addWorker(@Validated Worker worker, BindingResult result) {
		if (result.hasErrors()) {
			return "workerForm"; // Return to the form page with validation errors
		}

		// Calculate the salary based on attended days and salary per day
		double totalSalary = worker.getSalary() * worker.getAttendedDays();
		worker.setSalary(totalSalary); // Set the calculated total salary

		workerService.addWorker(worker); // Save the worker with calculated salary
		return "redirect:/shop/workers"; // Redirect to the workers list page
	}

	// Admin-specific endpoint to show the form for editing an existing worker
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/worker/edit")
	public String showEditWorkerForm(@RequestParam("id") Long id, Model model) {
		Worker worker = workerService.getWorkerById(id);
		if (worker == null) {
			return "redirect:/shop/workers?error=workerNotFound"; // Redirect if worker not found
		}
		model.addAttribute("worker", worker); // Add the worker details to the model
		return "workerForm"; // Thymeleaf template for editing worker
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/edit")
	public String updateWorker(@RequestParam("id") Long id, @Validated Worker workerDetails, BindingResult result) {
		if (result.hasErrors()) {
			return "workerForm"; // Return to the form page with validation errors
		}

		// Calculate the new total salary based on attended days and salary per day
		double totalSalary = workerDetails.getSalary() * workerDetails.getAttendedDays();
		workerDetails.setSalary(totalSalary); // Set the calculated salary

		// Update the worker's details by passing the worker ID and the updated Worker
		// object
		workerService.updateWorker(id, workerDetails);

		return "redirect:/shop/workers"; // Redirect to the workers list page
	}

	// Admin-specific endpoint to delete a worker
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/worker/delete")
	public String deleteWorker(@RequestParam("id") Long id) {
		Worker worker = workerService.getWorkerById(id);
		if (worker == null) {
			return "redirect:/shop/workers?error=workerNotFound"; // Redirect if worker not found
		}
		workerService.deleteWorker(id); // Delete the worker
		return "redirect:/shop/workers"; // Redirect to the workers list page after success
	}

	// Worker and Admin-specific endpoint to view all workers
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/workers")
	public String viewWorkers(Model model) {
		List<Worker> workers = workerService.getAllWorkers(); // Fetch all workers model.addAttribute("workers",
		model.addAttribute("workers", workers); // workers); // Add the list of workers to the model
		return "workersList"; // Thymeleaf template to display
		// worker details
	}

}
