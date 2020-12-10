package com.hao.basics.string;

public class JoinChar {
    public static void main(String[] args) {
        // 请将下面一组int值视为字符的Unicode码，把它们拼成一个字符串：
        int a = 72;
        int b = 105;
        int c = 65281;
        // String s = "\\u" + a + "\\u" + b + "\\u" + c;
        String s = "" + (char)a + (char)b + (char)c;
        // String s = "" + a + b + c;
        System.out.println(s);
    }
}
