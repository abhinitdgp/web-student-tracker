<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page import="java.util.*,org.abhishek.web.model.*"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<%-- <%
	List<Student> students = (List<Student>) request.getAttribute("student_list");
%> --%>
<body>

	<div id="wrapper">
		<div id="header">
			<h2 align="center">Test University</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<h3 align="center">STUDENTS LIST</h3>
			<hr>

			<!--Add new button-- to add student to the list  -->
			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp';return false;"
				class="add-student-button" />


			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<%-- <%
					for (Student tempStudent : students) {
				%> --%>

				<c:forEach var="tempStudent" items="${student_list}">
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="Load"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>

					<!-- set up a link to delete a student -->
					<c:url var="tempDelLink" value="StudentControllerServlet">
						<c:param name="command" value="Delete"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>

					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td><a href="${tempLink}">Update</a> | <a
							href="${tempDelLink}"
							onclick="if(!(confirm('Are you sure you want to this student?'))) return false;">Delete</a></td>
					</tr>
				</c:forEach>
				<%-- <tr>
					<td><%=tempStudent.getFirstName()%></td>
					<td><%=tempStudent.getLastName()%></td>
					<td><%=tempStudent.getEmail()%></td>
				</tr> --%>
				<%-- <%
					}
				%> --%>




			</table>
		</div>

	</div>

</body>
</html>