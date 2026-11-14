<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.campusconnect.model.Student" %>

<%
    List<Student> students =
        (List<Student>) request.getAttribute("students");

    Integer total =
        (Integer) request.getAttribute("totalStudents");

    if (total == null) total = 0;
%>

<!DOCTYPE html>
<html>
<head>
    <title>CampusConnect | Dashboard</title>
    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/style.css">
</head>

<body>

<div class="sidebar">
    <h2>CampusConnect</h2>

    <a class="active" href="<%= request.getContextPath() %>/students">
        Dashboard
    </a>

    <a href="<%= request.getContextPath() %>/students">
        Students
    </a>

    <a href="#" onclick="alert('Attendance module coming soon!')">
        Attendance
    </a>

    <a href="#" onclick="alert('Results module coming soon!')">
        Results
    </a>

    <a href="#" onclick="alert('Courses module coming soon!')">
        Courses
    </a>
</div>

<div class="main">

    <header>
        <div>
            <h1>Student Dashboard</h1>
            <p>Welcome to CampusConnect</p>
        </div>

        <a class="add-btn"
           href="<%= request.getContextPath() %>/students/new">
            + Add Student
        </a>
    </header>

    <div class="stats">

        <div class="card">
            <h3>Total Students</h3>
            <h1><%= total %></h1>
        </div>

        <div class="card">
            <h3>Portal Status</h3>
            <h1>Active</h1>
        </div>

        <div class="card">
            <h3>Academic System</h3>
            <h1>2026</h1>
        </div>

    </div>

    <div class="table-container">

        <h2>Student Records</h2>

        <form method="get"
              action="<%= request.getContextPath() %>/students">

            <input type="text"
                   name="search"
                   placeholder="Search students...">

            <button type="submit">Search</button>
        </form>

        <table>

            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Course</th>
                    <th>Semester</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>

            <% if (students != null && !students.isEmpty()) {
                for (Student s : students) { %>

                <tr>
                    <td><%= s.getId() %></td>
                    <td><%= s.getName() %></td>
                    <td><%= s.getEmail() %></td>
                    <td><%= s.getPhone() %></td>
                    <td><%= s.getCourse() %></td>
                    <td><%= s.getSemester() %></td>

                    <td>
                        <a href="<%= request.getContextPath() %>/students/edit?id=<%= s.getId() %>">
                            Edit
                        </a>

                        <form method="post"
                              action="<%= request.getContextPath() %>/students"
                              style="display:inline">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="id"
                                   value="<%= s.getId() %>">

                            <button type="submit"
                                    onclick="return confirm('Delete this student?')">
                                Delete
                            </button>
                        </form>
                    </td>
                </tr>

            <% }
            } else { %>

                <tr>
                    <td colspan="7">No students found.</td>
                </tr>

            <% } %>

            </tbody>
        </table>

    </div>

</div>

</body>
</html>
