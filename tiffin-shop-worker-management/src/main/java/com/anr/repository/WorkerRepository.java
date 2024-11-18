package com.anr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anr.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
	List<Worker> findAll();
}
