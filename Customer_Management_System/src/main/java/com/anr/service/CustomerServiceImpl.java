package com.anr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anr.model.Customer;
import com.anr.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		if (customerRepository.existsById(id)) {
			customer.setId(id);
			return customerRepository.save(customer);
		}
		return null;
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
}
