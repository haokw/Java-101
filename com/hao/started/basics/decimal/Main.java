package com.hao.started.basics.decimal;

/**
 * 求解 一元二次方程ax^2+bx+c=0
 */
public class Main {
    public static void main(String[] args) {
        // a * x ^ 2 + b * x + c = 0
        double a = 1.0;
        double b = 3.0;
        double c = -4.0;

        double r = Math.sqrt(b * b - 4 * a * c);
        double r1 = (-b + r) / (2 * a);
        double r2 = (-b - r) / (2 * a);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r1 == 1 && r2 == -4 ? "SUCCESS" : "FAIL");

        double rr1 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
        double rr2 = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);

        System.out.println(rr1);
        System.out.println(rr2);
        System.out.println(rr1 == 1 && rr2 == -4 ? "SUCCESS" : "FAIL");
    }
}
