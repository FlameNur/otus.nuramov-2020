package com.nuramov.hw02DIYarrayList;

import java.util.List;
import java.util.Random;

public class MyTestClass {
    public static void main(String[] args) {
        List<Integer> list = new DIYarrayList<>();
        Random random = new Random();

        int min = 100;
        int max = 200;
        int diff = max - min;

        for (int i = 0; i < 30; i++) {
            list.add(random.nextInt(diff + 1));
        }

        /*
        Проверить, что на ней работают методы из java.util.Collections:
            addAll(Collection<? super T> c, T... elements)
            static <T> void copy(List<? super T> dest, List<? extends T> src)
            static <T> void sort(List<T> list, Comparator<? super T> c)
         */
    }
}
