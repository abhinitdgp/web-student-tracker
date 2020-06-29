package org.abhishek.web.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.abhishek.web.helper.StudentDbUtil;
import org.abhishek.web.model.Student;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;

	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			// create our db util and pass in the connection pool/datasource
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// list the student in MVC pattern
		try {
			// read the command parameter
			String command = request.getParameter("command");

			// if the command is missing, default it to listing
			if (command == null || command.trim().isEmpty())
				command = "List";
			// route to appropriate method
			switch (command) {
			case "Add":
				addStudent(request, response);
				break;
			case "List":
				// list students in MVC pattern
				listStudents(request, response);
				break;

			default:
				listStudents(request, response);
			}

		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create new student object
		Student student = new Student(firstName, lastName, email);

		// save the student to the db
		studentDbUtil.addStudent(student);

		// send back to the main page(the student list)
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students from db util
		List<Student> students = studentDbUtil.getStudentDetails();

		// add student object to request attribute
		request.setAttribute("student_list", students);

		// send to JSP page(view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
