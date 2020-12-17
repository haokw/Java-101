package com.hao.learnjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int count;

        queryStudents().forEach(System.out::println);

        count = queryCount();
        System.out.println("student count : " + count);

        System.out.println("insert new student...");
        String sql = "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "大白");
                ps.setBoolean(2, true);
                ps.setInt(3, 3);
                ps.setInt(4, 97);
                int n = ps.executeUpdate();
                System.out.println(n + " inserted.");
            }
        }

        System.out.println("insert new student and return id...");
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setString(1, "小王");
                ps.setBoolean(2, true);
                ps.setInt(3, 3);
                ps.setInt(4, 99);
                int n = ps.executeUpdate();
                long id = 0;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
                System.out.println(n + " inserted. id = " + id);
            }
        }

        System.out.println("update student...");
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE students set score = ? WHERE name = ?")) {
                ps.setInt(1, 99);
                ps.setString(2, "小宝");
                int n = ps.executeUpdate();
                System.out.println(n + " updated.");
            }
        }

        System.out.println("delete student...");
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE score < ?")) {
                ps.setInt(1, 80);
                int n = ps.executeUpdate();
                System.out.println(n + " deleted.");
            }
        }

        queryStudents().forEach(System.out::println);

        count = queryCount();
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
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

        // std.setId(rs.getLong(1));
        // std.setName(rs.getString(2));
        // std.setGender(rs.getBoolean(3));
        // std.setGrade(rs.getInt(4));
        // std.setScore(rs.getInt(5));

        std.setId(rs.getLong("id"));
        std.setName(rs.getString("name"));
        std.setGender(rs.getBoolean("gender"));
        std.setGrade(rs.getInt("grade"));
        std.setScore(rs.getInt("score"));

        return std;
    }
}
