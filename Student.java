package com.campusconnect.dao;

import com.campusconnect.config.DBConnection;
import com.campusconnect.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Get all students
    public List<Student> getAllStudents() throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course"),
                    rs.getInt("semester")
                );

                students.add(s);
            }
        }

        return students;
    }

    // Add a student
    public void addStudent(Student s) throws SQLException {

        String sql = "INSERT INTO students " +
                "(name, email, phone, course, semester) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getCourse());
            ps.setInt(5, s.getSemester());

            ps.executeUpdate();
        }
    }

    // Update a student
    public void updateStudent(Student s) throws SQLException {

        String sql = "UPDATE students SET name=?, email=?, " +
                "phone=?, course=?, semester=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getCourse());
            ps.setInt(5, s.getSemester());
            ps.setInt(6, s.getId());

            ps.executeUpdate();
        }
    }

    // Delete a student
    public void deleteStudent(int id) throws SQLException {

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Search students
    public List<Student> searchStudents(String keyword)
            throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students " +
                "WHERE name LIKE ? OR email LIKE ? OR course LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("course"),
                        rs.getInt("semester")
                    ));
                }
            }
        }

        return students;
    }

    // Count students
    public int countStudents() throws SQLException {

        String sql = "SELECT COUNT(*) FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }
}
