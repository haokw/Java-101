package com.hao.learnjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Student> students = queryStudents();
        students.forEach(System.out::println);

        int count = queryCount();
        System.out.println("student count : " + count);
    }

    static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
    static final String jdbcUsername = "learn";
    static final String jdbcPassword = "";

    static int queryCount() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(1) FROM students";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        count = rs.getInt("COUNT(1)");
                    }
                }
            }
        }

        return count;
    }

    static List<Student> queryStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE grade = ? AND score >= ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, 3);
                ps.setInt(2, 90);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        students.add(extractRow(rs));
                    }
                }
            }
        }

        return students;
    }

    static Student extractRow(ResultSet rs) throws SQLException {
        Student std = new Student();
        std.setId(rs.getLong(1));
        std.setName(rs.getString(2));
        std.setGender(rs.getBoolean(3));
        std.setGrade(rs.getInt(4));
        std.setScore(rs.getInt(5));

        // std.setId(rs.getLong("id"));
        // std.setName(rs.getString("name"));
        // std.setGender(rs.getBoolean("gender"));
        // std.setGrade(rs.getInt("grade"));
        // std.setScore(rs.getInt("score"));

        return std;
    }
}
