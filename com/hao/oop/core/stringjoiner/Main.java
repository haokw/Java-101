package com.hao.oop.core.stringjoiner;

import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        String[] fields = {"name", "position", "salary"};
        String table = "employee";
        String select = buildSelectSql(table, fields);
        System.out.println(select);
        System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "SUCCESS" : "FAIL");
    }

    private static String buildSelectSql(String table, String[] fields) {
        StringJoiner select = new StringJoiner(", ", "SELECT ", " FROM " + table);
        for (String field : fields) {
            select.add(field);
        }
        return select.toString();
    }
}
