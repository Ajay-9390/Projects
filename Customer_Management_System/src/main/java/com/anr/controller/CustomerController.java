package com.anr.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anr.model.Customer;
import com.anr.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String getAllCustomers(Model model) {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "index"; // Return to "index.html" with the customer list.
	}

	// "Add New Customer" form
	@GetMapping("/new")
	public String newCustomerForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "newCustomer"; // "newCustomer.html" for adding a new customer
	}

	// Save a new customer after form submission
	@PostMapping("/save")
	public String saveCustomer(@ModelAttribute Customer customer) {
		customerService.addCustomer(customer); // Save the new customer to the database
		return "redirect:/customers"; // Redirect to list of customers after saving
	}

	// Editing an existing customer and error handling
	@GetMapping({ "/edit/{id}", "/edit", "/edit/" })
	public String editCustomerForm(@PathVariable(required = false) Long id, Model model) {

		if (id == null) {
			model.addAttribute("error", "Invalid Request: No ID provided.");
			return "errorPage"; // Redirect to "errorPage.html" when no ID is provided.
		}

		Optional<Customer> customer = customerService.getCustomerById(id);
		if (customer.isPresent()) {
			model.addAttribute("customer", customer.get());
			return "editCustomer"; // Show the edit form if customer exists
		} else {
			model.addAttribute("error", "Invalid ID: Customer not found.");
			return "errorPage"; // Redirect to "errorPage.html" when entered an invalid id.
		}
	}

	// Updating an existing customer
	@PostMapping("/update")
	public String updateCustomer(@ModelAttribute Customer customer, Model model) {

		Optional<Customer> existingCustomer = customerService.getCustomerById(customer.getId());
		if (existingCustomer.isPresent()) {
			customerService.updateCustomer(customer.getId(), customer);
			return "redirect:/customers"; // Redirecting after successful update.
		} else {
			model.addAttribute("error", "Invalid ID: Customer not found.");
			return "errorPage"; // Redirect to error page
		}
	}

	// Delete the customer
	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id); // Delete the customer from the database
		return "redirect:/customers"; // Redirecting to the customer list after deletion
	}
}
