package com.hao.collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 构造从start到end的序列：
        final int start = 10;
        final int end = 20;
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }

        // 洗牌算法suffle可以随机交换List中的元素位置:
        Collections.shuffle(list);

        // 随机删除List中的一个元素:
        int removed = list.remove((int) (Math.random() * list.size()));

        // 找出从start到end的缺失的数字并返回
        int found = findMissingNumber(start, end, list);
        System.out.println(list.toString());
        System.out.println("missing number: " + found);
        System.out.println(removed == found ? "SUCCESS" : "FAIL");
    }

    static int findMissingNumber(int start, int end, List<Integer> list) {
        int ret = 0;

        for (int i = start; i <= end; i++) {
            if (! list.contains(i)) {
                ret = i;
                break;
            }
        }

        return ret;
    }

    // static int findMissingNumber(int start, int end, List<Integer> list) {
    //     int ret = 0;
    //     int i = start;

    //     for (Integer ii : list) {
    //         if (ii != i) {
    //             ret = i;
    //             break;
    //         } else {
    //             i++;
    //         }
    //     }

    //     return ret;
    // }
}
