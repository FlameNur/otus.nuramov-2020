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
        for (Integer i : list1) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("Проверяем метод Collections.addAll: ");
        Collections.addAll(list1, 27, 28, 29999);
        for (Integer i : list1) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 100; i < 130; i++) {
            list2.add(i);
        }

        System.out.print("Содержание list2: ");
        for (Integer i : list2) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 30; i > 0; i--) {
            list3.add(i);
        }

        System.out.print("Содержание list3: ");
        for (Integer i : list3) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("Проверяем метод Collections.sort для list3: ");
        Collections.sort(list3);
        for (Integer i : list3) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Проверяем метод Collections.copy: ");
        System.out.print("list2 до Collections.copy: ");
        for (Integer i : list2) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("list2 после Collections.copy: ");
        Collections.copy(list2, list1);
        for (Integer i : list2) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("______________________________________");

        List<String> list111 = new DIYarrayList<>();

        list111.add("A");
        list111.add("B");
        list111.add("C");
        list111.add("D");
        list111.set(2, "BBS");
        list111.add(4, "111");

        List<String> list222 = new DIYarrayList<>();

        list222.add("E");
        list222.add("F");

        //list111.addAll(list222);      // НЕ РАБОТАЕТ addAll

        for (String i : list111) {
            System.out.print(i + " ");
        }
        System.out.println();

        list111.remove(1);

        for (String i : list111) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
