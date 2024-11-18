package com.anr;

public class WorkerNotFoundException extends RuntimeException {
	public WorkerNotFoundException(String message) {
		super(message);
	}
}
