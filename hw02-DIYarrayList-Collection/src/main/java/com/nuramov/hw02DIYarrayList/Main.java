package com.nuramov.hw02DIYarrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new DIYarrayList<>();
        //Random random = new Random();

        //int min = 100;
        //int max = 200;
        //int diff = max - min;
        /*
        for (int i = 0; i < 30; i++) {
            //list.add(random.nextInt(diff));
            list.add(i);
        }

        for(Integer i : list) {
            System.out.print(i + " ");
        }


        List<Integer> list2 = new DIYarrayList<>();
        list2.add(100);
        list2.add(101);

        list.addAll(3, list2);

        for(Integer i : list) {
            System.out.print(i + " ");
        }
        System.out.println();

         */

        Collections.addAll(list, 31, 32, 33);

        for(int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }



        /*
        Проверить, что на ней работают методы из java.util.Collections:
            addAll(Collection<? super T> c, T... elements)
            static <T> void copy(List<? super T> dest, List<? extends T> src)
            static <T> void sort(List<T> list, Comparator<? super T> c)
         */
    }
}
