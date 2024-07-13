package com.anr.service;

import com.anr.dto.Student;

public interface IStudentService {
	
	//Operations to be implemented
	
	public String addStudent(String sname,Integer sage, String saddress );

	public Student searchStudent(Integer sid);

	public String updateStudent(Student student);

	public String deleteStudent(Integer sid);

}
