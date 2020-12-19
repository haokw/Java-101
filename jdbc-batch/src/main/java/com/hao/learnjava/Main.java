package com.hao.learnjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        queryStudents().forEach(System.out::println);
        batchInsertStudents(Arrays.asList("Cyber", "Punk", "Big Head"), false, 3, 99);
        queryStudents().forEach(System.out::println);
    }

    private static void batchInsertStudents(List<String> names, boolean gender, int grade, int score) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            boolean isAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (String name : names) {
                    ps.setString(1, name);
                    ps.setBoolean(2, gender);
                    ps.setInt(3, grade);
                    ps.setInt(4, score);
                    ps.addBatch();
                }
                int[] ns = ps.executeBatch();
                for (int n : ns) {
                    System.out.println(n + " inserted.");
                }
            }
            conn.commit();
            conn.setAutoCommit(isAutoCommit);
        }
    }

    private static List<Student> queryStudents() throws SQLException {
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

    private static int queryCount() throws SQLException {
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

    private static Student extractRow(ResultSet rs) throws SQLException {
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

    private static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static final String jdbcUsername = "learn";
    private static final String jdbcPassword = "";
}
