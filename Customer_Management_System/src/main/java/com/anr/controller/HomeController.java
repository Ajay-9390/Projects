package com.anr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.anr.service.CustomerService;

@Controller
public class HomeController {

	@Autowired
	private CustomerService customerService;

	// Handling requests to root URL "/" and "/customers"
	@GetMapping({ "", "/" })
	public String home(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());
		return "index"; // Returning the "index.html" to display the customer list
	}
}
