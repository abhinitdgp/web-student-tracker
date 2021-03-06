package org.abhishek.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DataSource/connection poll for Resource Injection
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.set up print writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		// 2. Get a connection to database
		Connection myConn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			myConn = dataSource.getConnection();

			// 3.create sql statement
			String sql = "select * from student;";
			stmt = myConn.createStatement();

			// 4. execute sql query
			rs = stmt.executeQuery(sql);

			// 5.process result set
			while (rs.next()) {
				String email = rs.getString("email");
				out.println(email);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
