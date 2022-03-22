package com.example.oj.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成考试信息，可进一步优化，直接生成excel文件
 * @author sjy
 * @date 2022/3/10
 **/
public class RandomSelect {

    /**
     * 随机生成选择题选项
     */
    @Test
    public void test() {
        for (int i = 0; i < 60; i++) {
            String str = "";
            Integer score = 0;
            for (int i1 = 0; i1 < 3; i1++) {
                char c=(char)('A'+Math.random()*('C'-'A'+1));
                str += c + ";";
                if(i1==0&&c=='C'){
                    score++;
                }else if(i1==1&&c=='C'){
                    score++;
                }else if(i1==2&&c=='B'){
                    score++;
                }
            }
            System.out.println(str + " " + score*5);
        }
    }

    /**
     * 随机生成判断题选项
     */
    @Test
    public void test2() {
        for (int i = 0; i < 60; i++) {
            String str = "";
            Integer score = 0;
            for (int i1 = 0; i1 < 6; i1++) {
                char c=(char)((int)(10*Math.random())%2 + '0');
                str += c + ";";
                if(i1!=5 && c == '1') {
                    score++;
                } else if (i1==5&&c=='0') {
                    score++;
                }
            }
            System.out.println(str + " " + score*5);
        }
    }

    /**
     * 随机生成填空题答案
     */
    @Test
    public void test3() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"16");
        map.put(1,"逻辑关系");
        map.put(2,"结构");
        map.put(3,"序言性");
        map.put(4,"8");
        map.put(5,"逻辑关系");
        map.put(6,"结构");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            String str = "";
            int score = 0;
            for (int i1 = 0; i1 < 7; i1++) {
                char c=(char)((int)(10*Math.random())%2 + '0');
                if(c =='0') {
                    str += map.get(i1) + ";";
                    score++;
                }else {
                    str +=  "不会;";
                }
            }
            System.out.println(str);
            list.add(score*5);
        }
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
