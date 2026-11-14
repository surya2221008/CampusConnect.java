<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.campusconnect.model.Student" %>

<%
    Student student =
        (Student) request.getAttribute("student");

    boolean editing = student != null;
%>

<!DOCTYPE html>
<html>
<head>
    <title>
        <%= editing ? "Edit Student" : "Add Student" %>
    </title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/style.css">
</head>

<body>

<div class="main form-main">

    <h1>
        <%= editing ? "Edit Student" : "Add New Student" %>
    </h1>

    <form method="post"
          action="<%= request.getContextPath() %>/students">

        <input type="hidden"
               name="action"
               value="save">

        <% if (editing) { %>
            <input type="hidden"
                   name="id"
                   value="<%= student.getId() %>">
        <% } %>

        <label>Full Name</label>

        <input type="text"
               name="name"
               value="<%= editing ? student.getName() : "" %>"
               required>

        <label>Email Address</label>

        <input type="email"
               name="email"
               value="<%= editing ? student.getEmail() : "" %>"
               required>

        <label>Phone Number</label>

        <input type="text"
               name="phone"
               value="<%= editing ? student.getPhone() : "" %>">

        <label>Course</label>

        <input type="text"
               name="course"
               value="<%= editing ? student.getCourse() : "" %>"
               required>

        <label>Semester</label>

        <input type="number"
               name="semester"
               min="1"
               max="12"
               value="<%= editing ? student.getSemester() : 1 %>"
               required>

        <button type="submit">
            <%= editing ? "Update Student" : "Save Student" %>
        </button>

        <a href="<%= request.getContextPath() %>/students">
            Cancel
        </a>

    </form>

</div>

</body>
</html>
