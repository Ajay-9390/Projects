package com.anr.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.anr.dto.Student;
import com.anr.util.JdbcUtil;

public class StudentDaoImpl implements IStudentDao{

	Connection connection = null;
	PreparedStatement pstmt   = null;
	ResultSet resultSet = null;
	Student student;

	@Override
	public String addStudent(String sname, Integer sage, String saddress) {

		String sqlInsertQuery = "Insert into student(sname,sage,saddress) values(?,?,?)";

		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null)
				pstmt = connection.prepareStatement(sqlInsertQuery);
			if (pstmt != null) {
				pstmt.setString(1, sname);
				pstmt.setInt(2, sage);
				pstmt.setString(3, saddress);

				int rowAffected = pstmt.executeUpdate();

				if (rowAffected == 1) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}

	@Override
	public Student searchStudent(Integer sid) {

		String sqlSelectQuery = "select sid,sname,sage,saddress from student where sid = ?";

		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);

			if (pstmt != null) {
				pstmt.setInt(1, sid);
			}

			if (pstmt != null) {
				resultSet = pstmt.executeQuery();
			}

			if (resultSet != null) {
				if (resultSet.next()) {
					student = new Student();

					//copy resultSet data to student object
					student.setSid(resultSet.getInt(1));
					student.setSname(resultSet.getString(2));
					student.setSage(resultSet.getInt(3));
					student.setSaddress(resultSet.getString(4));

					return student;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public String updateStudent(Student student) {


		String sqlUpdateQuery = "Update student set sname=?, sage=?, saddress=? where sid=?";

		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null)
				pstmt = connection.prepareStatement(sqlUpdateQuery);

			if (pstmt != null) {
				
				pstmt.setString(1, student.getSname());
				pstmt.setInt(2, student.getSage());
				pstmt.setString(3, student.getSaddress());
				pstmt.setInt(4, student.getSid());

				int rowAffected = pstmt.executeUpdate();
				if (rowAffected == 1) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}

	
	@Override
	public String deleteStudent(Integer sid) {


		String sqlDeleteQuery = "Delete from student where sid = ?";

		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null)
				pstmt = connection.prepareStatement(sqlDeleteQuery);

			if (pstmt != null) {
				pstmt.setInt(1, sid);
				int rowAffected = pstmt.executeUpdate();
				if (rowAffected == 1) {
					return "success";
				} else 
					return "not found";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}

}
