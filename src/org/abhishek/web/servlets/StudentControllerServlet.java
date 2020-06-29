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
			case "Load":
				loadStudent(request, response);
				break;
			case "Update":
				updateStudent(request, response);
				break;
			case "Delete":
				deleteStudent(request, response);
				break;

			default:
				listStudents(request, response);
			}

		} catch (Exception ex) {
			throw new ServletException(ex);
		}

	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get Student Id from form data
		int studentId = Integer.parseInt(request.getParameter("studentId"));

		// update db to delete student on the basis of id
		studentDbUtil.deleteStudent(studentId);

		// send back to the list students page
		listStudents(request, response);

	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get student info from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create student object
		Student student = new Student(id, firstName, lastName, email);

		// perform update on the db
		studentDbUtil.updateStudent(student);

		// send the data back to list students page
		listStudents(request, response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student id from the request
		String studentId = request.getParameter("studentId");

		// get student from db
		Student student = studentDbUtil.loadStudent(studentId);

		// set that student in request attribute
		request.setAttribute("student", student);

		// send to jsp page:update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);

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
