package org.abhishek.web.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.abhishek.web.model.Student;

public class StudentDbUtil {
	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}

	public List<Student> getStudentDetails() throws SQLException {
		List<Student> students = new ArrayList<Student>();
		Connection myConn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			myConn = dataSource.getConnection();

			stmt = myConn.createStatement();
			String sql = "select * from student;";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				students.add(new Student(firstName, lastName, email));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close JDBC objects
			close(myConn, stmt, rs);
		}
		return students;
	}

	private void close(Connection myConn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (myConn != null)
				myConn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void addStudent(Student student) throws SQLException {

		Connection myConn = null;
		PreparedStatement stmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create sql for insert
			String sql = "INSERT INTO student (first_name,last_name,email) values(?,?,?)";
			stmt = myConn.prepareStatement(sql);

			// add parameters for student
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmail());

			// execute the sql
			stmt.execute();
		} finally {
			// clean up JDBC object
			close(myConn, stmt, null);
		}

	}
}
