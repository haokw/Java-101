package com.hao.started.control.input;

import java.util.Scanner;

/**
 * 输入上次考试成绩（int）和本次考试成绩（int），然后输出成绩提高的百分比，保留两位小数位（例如，21.75%）
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the last test score: ");
        int prev = scanner.nextInt();
        System.out.print("Input this test score: ");
        int score = scanner.nextInt();
        double percent = score / (double)prev;
        System.out.printf("成绩提高了?%.2f\n", percent);
        scanner.close();
    }
}
