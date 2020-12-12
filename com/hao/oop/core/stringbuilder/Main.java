package com.hao.oop.core.stringbuilder;

public class Main {
    public static void main(String[] args) {
        String[] fields = {"name", "position", "salary"};
        String table = "employee";
        String insert = buildInsertSql(table, fields);
        System.out.println(insert);
        System.out.println(
                "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)".equals(insert) ? "SUCCESS" : "FAIL");
    }

    private static String buildInsertSql(String table, String[] fields) {
        StringBuilder insert = new StringBuilder(256);
        insert.append("INSERT INTO ")
            .append(table)
            .append(" (")
            .append(String.join(", ", fields))
            .append(") ")
            .append("VALUES (?, ?, ?)");
        return insert.toString();
    }
}
