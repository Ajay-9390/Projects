package com.anr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anr.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
