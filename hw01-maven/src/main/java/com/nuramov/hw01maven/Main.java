package com.nuramov.hw01maven;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp);                       // {5, 4, 3, 2, 1}

        List<List<Integer>> parts = Lists.partition(countDown, 2);         // {{5, 4}, {3, 2}, {1}}

        for(List<Integer> list : parts) {
            System.out.println(list);
        }
    }
}
