package com.anr.service;

import java.util.List;
import java.util.Optional;

import com.anr.model.Customer;

public interface CustomerService {

	List<Customer> getAllCustomers();

	Optional<Customer> getCustomerById(Long id);

	Customer addCustomer(Customer customer);

	Customer updateCustomer(Long id, Customer customer);

	void deleteCustomer(Long id);
}
