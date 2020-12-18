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
        queryStudents().forEach(System.out::println);

        insertStudents("小黄", true, 3, 100);
        insertStudents("小头", false, 3, 101);

        queryStudents().forEach(System.out::println);
    }

    public static void insertStudents(String name, boolean gender, int grade, int score) throws SQLException {
        System.out.println("insert new student...");
        String sql = "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            boolean isAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setBoolean(2, gender);
                ps.setInt(3, grade);
                ps.setInt(4, score);
                int n = ps.executeUpdate();
                System.out.println(n + " inserted.");
            }
            if (score > 100) {
                conn.rollback();
                System.out.println("rollback.");
            } else {
                conn.commit();
                System.out.println("commit.");
            }
            conn.setAutoCommit(isAutoCommit);
        }
    }

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

        int count = queryCount();
        System.out.println("student count : " + count);

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

    static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
    static final String jdbcUsername = "learn";
    static final String jdbcPassword = "";
}
