package com.campusconnect.servlet;

import com.campusconnect.dao.StudentDAO;
import com.campusconnect.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {

    private StudentDAO dao;

    @Override
    public void init() {
        dao = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();

        try {

            if (action == null || action.equals("/")) {

                String keyword = request.getParameter("search");

                List<Student> students;

                if (keyword != null && !keyword.trim().isEmpty()) {
                    students = dao.searchStudents(keyword);
                } else {
                    students = dao.getAllStudents();
                }

                request.setAttribute("students", students);
                request.setAttribute("totalStudents",
                        dao.countStudents());

                request.getRequestDispatcher(
                        "/WEB-INF/views/students.jsp")
                        .forward(request, response);

            } else if (action.equals("/new")) {

                request.getRequestDispatcher(
                        "/WEB-INF/views/student-form.jsp")
                        .forward(request, response);

            } else if (action.equals("/edit")) {

                int id = Integer.parseInt(
                        request.getParameter("id"));

                Student student = dao.getAllStudents()
                        .stream()
                        .filter(s -> s.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (student == null) {
                    response.sendError(404);
                    return;
                }

                request.setAttribute("student", student);

                request.getRequestDispatcher(
                        "/WEB-INF/views/student-form.jsp")
                        .forward(request, response);
            }

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {

            if ("save".equals(action)) {

                Student student = new Student();

                String id = request.getParameter("id");

                if (id != null && !id.isEmpty()) {
                    student.setId(Integer.parseInt(id));
                }

                student.setName(request.getParameter("name"));
                student.setEmail(request.getParameter("email"));
                student.setPhone(request.getParameter("phone"));
                student.setCourse(request.getParameter("course"));
                student.setSemester(
                        Integer.parseInt(request.getParameter("semester")));

                if (student.getId() > 0) {
                    dao.updateStudent(student);
                } else {
                    dao.addStudent(student);
                }

            } else if ("delete".equals(action)) {

                int id = Integer.parseInt(
                        request.getParameter("id"));

                dao.deleteStudent(id);
            }

            response.sendRedirect(
                    request.getContextPath() + "/students");

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
