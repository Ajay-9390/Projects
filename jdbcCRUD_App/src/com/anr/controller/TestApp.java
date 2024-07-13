package com.anr.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import com.anr.dto.Student;
import com.anr.service.IStudentService;
import com.anr.servicefactory.StudentServiceFactory;

public class TestApp {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while(true) {
			
			System.out.println("1. CREATE");
			System.out.println("2. READ");
			System.out.println("3. UPDATE");
			System.out.println("4. DELETE");
			System.out.println("5. EXIT");
			
			String option = br.readLine();
			
			switch (option) {
			case "1":insertOperation();
			break;
			
			case "2":selectOperation();
			break;
			
			case "3":updateOperation();
			break;
			
			case "4":deleteOperation();
			break;
			
			case "5":
				System.out.println("THANK YOU FOR USING THE APPLICATION :) ");
			break;
			
			default:
				System.out.println("Invalid option please try again with a valid option..");	
			}	
		}	
	}
	
	private static void updateOperation() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter student id to be updated : ");
		String sid = br.readLine();
		
		IStudentService studentService = StudentServiceFactory.getStudentService();
		Student student = studentService.searchStudent(Integer.parseInt(sid));
		
		if (student != null) {
			Student newStudent = new Student();
			
			System.out.println("Student id is : " + student.getSid());
			newStudent.setSid(student.getSid());
			
			System.out.println("Student oldName is : " + student.getSname() + " Enter newName :");
			String newName = br.readLine();
			if (newName.equals("") || newName == "") {
				newStudent.setSname(student.getSname());
			} else {
				newStudent.setSname(newName);
			}
			
			System.out.println("Student oldAge is : " + student.getSage() + " Enter newAge :");
			String newAge = br.readLine();
			if (newAge.equals("") || newAge == "") {
				newStudent.setSage(student.getSage());
			} else {
				newStudent.setSage(Integer.parseInt(newAge));
			}
			
			System.out.println("Student oldAddress is : " + student.getSaddress() + " Enter newAddress :");
			String newAddress = br.readLine();
			if (newAddress.equals("") || newAddress == "") {
				newStudent.setSaddress(student.getSaddress());
			} else {
				newStudent.setSaddress(newAddress);
			}
			
			String status = studentService.updateStudent(newStudent);
			if (status.equalsIgnoreCase("success")) {
				System.out.println("Record updated successfully..");
			} else {
				System.out.println("Record updation has failed");
			}
		} else {
			System.out.println("Student record is not available for the given id :" + sid + "for updation");
		}
	}
	
	private static void deleteOperation() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter student id:");
		Integer sid = scanner.nextInt();
		
		IStudentService studentService = StudentServiceFactory.getStudentService();
		String msg = studentService.deleteStudent(sid);
		
		if (msg.equalsIgnoreCase("success")) {
			System.out.println("Record deleted successfully..");
		} else if (msg.equalsIgnoreCase("not found")){
			System.out.println("Record not available for tha given id : " + sid );
		} else {
			System.out.println("Record deletion has failed");
		}
	}
	
	private static void selectOperation() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter student id:");
		Integer sid = scanner.nextInt();

		IStudentService studentService = StudentServiceFactory.getStudentService();
		Student std = studentService.searchStudent(sid);
		if (std != null) {
			//System.out.println(std);
			System.out.println("SID\tSNAME\tSAGE\tSADDRESS");
			System.out.println(std.getSid() + "\t" + std.getSname() + "\t" + std.getSage() + "\t" + std.getSaddress());
		} else {
			System.out.println("Record not found for the given id : " + sid);
		}
	}
	
	private static void insertOperation() {

		IStudentService studentService = StudentServiceFactory.getStudentService();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter student name:");
		String sname = scanner.next();

		System.out.println("Enter student age:");
		int sage = scanner.nextInt();

		System.out.println("Enter student address:");
		String saddress = scanner.next();

		String msg = studentService.addStudent(sname, sage, saddress);
		if (msg.equalsIgnoreCase("success")) {
			System.out.println("Yayyy...Record Insertion is Successful");
		} else {
			System.out.println("Record Insertion has failed, Please check your code!");
		}
	}

}
