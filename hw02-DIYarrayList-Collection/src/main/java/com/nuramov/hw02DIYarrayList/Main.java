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

        System.out.print("Проверяем метод Collections.addAll(): ");
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

        System.out.print("Проверяем метод Collections.sort() для list3: ");
        Collections.sort(list3);
        for (Integer i : list3) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Проверяем метод Collections.copy(): ");
        System.out.print("list2 до Collections.copy: ");
        for (Integer i : list2) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("list2 после Collections.copy(): ");
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

        for (String i : list111) {
            System.out.print(i + " ");
        }
        System.out.println();

        list111.remove(1);

        for (String i : list111) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println(list111.get(2));

        list111.clear();
        System.out.print("Проверка метода .clear(): ");
        for (String i : list111) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("Проверка метода .toArray(): ");
        Object[] obj = list1.toArray();
        for (Object o: obj) {
            System.out.print(o + " ");
        }
        System.out.println();


        System.out.print("Проверка метода .toArray(a[]): ");
        List<Integer> list444 = new DIYarrayList<>();
        list444.add(10);
        list444.add(20);
        list444.add(30);
        list444.add(40);
        /*
            Заметил такую "штуку":
            Проверка производилась для метода .toArray(a[]) классов ArrayList и DIYarrayList.
            Если лист имеет, например, 4 элемента (от 0 до 3) - size = 4, вывод будет следующий:
            1.
            Integer[] arr = new Integer[3];  // arr.length = 3
            arr = list444.toArray(arr);
            Вывод: 1 2 3 4
            2.
            Integer[] arr = new Integer[4];  // arr.length = 4
            arr = list444.toArray(arr);
            Вывод: 1 2 3 4                   // Вывод такой же, как и при arr.length =3
            3.
            Integer[] arr = new Integer[5];  // arr.length = 5
            arr = list444.toArray(arr);
            Вывод: 1 2 3 4 null
         */
        Integer[] arr = new Integer[4];
        arr = list444.toArray(arr);

        for (Integer x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Проверка метода .remove(index): ");
        list1.remove(5);
        System.out.print("Содержание list1 после .remove(index): ");
        for (Integer i : list1) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Проверка метода .remove(Object o): ");
        List<Integer> list5 = new DIYarrayList<>();
        list5.add(10);
        list5.add(20);
        list5.add(20);
        list5.add(30);
        list5.add(40);
        list5.add(20);

        System.out.print("До удаления элемента из list5: ");
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        list5.remove(new Integer(20));
        System.out.print("После удаления элемента из list5: ");
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        list5.remove(null);
        System.out.print("После удаления null из list5: ");
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.print("Проверка метода .indexOf(Object o): ");
        System.out.print(list5.indexOf(40));
        System.out.println();

        System.out.print("Проверка метода .contains(Object o): ");
        System.out.print(list5.contains(40));
        System.out.println();

        System.out.print("Проверка метода .lastIndexOf(Object o): ");
        list5.add(10);
        list5.add(40);
        list5.add(40);
        list5.add(50);
        list5.add(40);
        list5.add(40);
        list5.add(40);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(10);
        System.out.print(list5.lastIndexOf(40));
        System.out.println();

        System.out.println("Проверка метода .removeAll: ");
        List<Integer> list6 = new DIYarrayList<>();
        list6.add(4000);
        list6.add(10);
        list6.add(70);


        System.out.println("list5 до .removeAll: ");
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        list5.removeAll(list6);
        System.out.println("list5 после .removeAll: ");
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Проверка метода .addAll(Collection<? extends T> c): ");
        list5.addAll(list6);
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Проверка метода .addAll(int index, Collection<? extends T> c): ");
        List<Integer> list7 = new DIYarrayList<>();
        list7.add(5555);
        list7.add(8888);
        list5.addAll(5, list7);
        for (Integer x : list5) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
