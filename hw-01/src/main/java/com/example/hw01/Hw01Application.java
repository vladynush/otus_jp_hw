package com.example.hw01;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Hw01Application {

    public static void main(String[] args) {
        List<Integer> array = new ArrayList<>();

        int limit = 200;
        int count = 0;
        while (count < limit) {
            array.add(count++);
        }

        System.out.println(Lists.reverse(array));
    }

}
