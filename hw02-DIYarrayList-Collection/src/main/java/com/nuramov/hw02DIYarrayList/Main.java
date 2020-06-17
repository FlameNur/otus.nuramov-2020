package com.nuramov.hw02DIYarrayList;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new DIYarrayList<>();
        List<Integer> list2 = new DIYarrayList<>();
        List<Integer> list3 = new DIYarrayList<>();

        for (int i = 0; i < 27; i++) {
            list1.add(i);
        }

        System.out.print("Содержание list1: ");
        for (int i = 0; i < list1.size(); i++) {
            System.out.print(list1.get(i) + " ");
        }
        System.out.println();

        System.out.print("Проверяем метод Collections.addAll: ");
        Collections.addAll(list1, 27, 28, 29);
        for (int i = 0; i < list1.size(); i++) {
            System.out.print(list1.get(i) + " ");
        }
        System.out.println();

        for (int i = 100; i < 130; i++) {
            list2.add(i);
        }

        System.out.print("Содержание list2: ");
        for (int i = 0; i < list2.size(); i++) {
            System.out.print(list2.get(i) + " ");
        }
        System.out.println();

        for (int i = 30; i > 0; i--) {
            list3.add(i);
        }

        System.out.print("Содержание list3: ");
        for (int i = 0; i < list3.size(); i++) {
            System.out.print(list3.get(i) + " ");
        }
        System.out.println();

        System.out.print("Проверяем метод Collections.sort: ");
        Collections.sort(list3);
        for (int i = 0; i < list3.size(); i++) {
            System.out.print(list3.get(i) + " ");
        }
        System.out.println();


        // Пока не работает.
        System.out.print("Проверяем метод Collections.copy: ");
        Collections.copy(list2, list1);
        for (int i = 0; i < list1.size(); i++) {
            System.out.print(list1.get(i) + " ");
        }
        System.out.println();


    }
}
