package com.anr.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anr.WorkerNotFoundException;
import com.anr.model.Worker;
import com.anr.repository.WorkerRepository;

@Service
public class WorkerService {

	@Autowired
	private WorkerRepository workerRepository;

	public Worker addWorker(Worker worker) {
		return workerRepository.save(worker);
	}

	public void deleteWorker(Long id) {
		if (workerRepository.existsById(id)) {
			workerRepository.deleteById(id);
		} else {
			throw new WorkerNotFoundException("Worker not found with id " + id);
		}
	}

	public void updateWorker(Long id, Worker workerDetails) {
		// Find the existing worker by ID
		Worker existingWorker = workerRepository.findById(id)
				.orElseThrow(() -> new WorkerNotFoundException("Worker not found"));

		// Update the existing worker details with new values
		existingWorker.setName(workerDetails.getName());
		existingWorker.setRole(workerDetails.getRole());
		existingWorker.setAttendedDays(workerDetails.getAttendedDays());
		existingWorker.setSalary(workerDetails.getSalary()); // Update the salary with the new calculated value

		// Save the updated worker back to the database
		workerRepository.save(existingWorker);
	}

	public List<Worker> getAllWorkers() {
		return workerRepository.findAll();
	}

	public Worker getWorkerById(Long id) {
		return workerRepository.findById(id)
				.orElseThrow(() -> new WorkerNotFoundException("Worker not found with id " + id));
	}
}
