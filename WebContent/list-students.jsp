<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.abhishek.web.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<%
	List<Student> students = (List<Student>) request.getAttribute("student_list");
%>
<body>

	<div id="wrapper">
		<div id="header">
			<u><h2 align="center">Test University</h2></u>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<h3 align="center">STUDENTS LIST</h3>
			<hr>
			<table border="1">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				<%
					for (Student tempStudent : students) {
				%>
				<tr>
					<td><%=tempStudent.getFirstName()%></td>
					<td><%=tempStudent.getLastName()%></td>
					<td><%=tempStudent.getEmail()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>

	</div>

</body>
</html>