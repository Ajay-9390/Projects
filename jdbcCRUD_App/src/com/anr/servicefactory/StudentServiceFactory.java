package com.anr.servicefactory;

import com.anr.service.IStudentService;
import com.anr.service.StudentServiceImpl;

public class StudentServiceFactory {
	
	private StudentServiceFactory(){}

	private static IStudentService studentService = null;

	public static IStudentService getStudentService() {
		if (studentService == null) {
			studentService = new StudentServiceImpl();
		}
		return studentService;
	}

}
