package com.example.oj.utils;

import java.util.Random;

/**
 * @author sjy
 * @date 2022/2/16
 **/
public class RandomCode {
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
