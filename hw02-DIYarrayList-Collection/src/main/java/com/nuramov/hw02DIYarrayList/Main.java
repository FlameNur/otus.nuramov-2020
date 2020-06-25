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
        System.out.println(list1);

        System.out.print("Проверяем метод Collections.addAll(): ");
        Collections.addAll(list1, 27, 28, 29999);
        System.out.println(list1);

        for (int i = 100; i < 130; i++) {
            list2.add(i);
        }

        System.out.print("Содержание list2: ");
        System.out.println(list2);

        System.out.print("Содержание list3: ");
        for (int i = 30; i > 0; i--) {
            list3.add(i);
        }
        System.out.println(list3);

        System.out.print("Проверяем метод Collections.sort(list, null) для list3: ");           // Comparator = null
        Collections.sort(list3, null);
        System.out.println(list3);

        System.out.println("Проверяем метод Collections.copy(): ");
        System.out.print("list2 до Collections.copy: ");
        System.out.println(list2);

        System.out.print("list2 после Collections.copy(): ");
        Collections.copy(list2, list1);
        System.out.println(list2);

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

        System.out.println(list111);

        list111.remove(1);

        System.out.println(list111);
        System.out.println(list111.get(2));

        list111.clear();
        System.out.print("Проверка метода .clear(): ");
        System.out.println(list111);

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

        Integer[] arr = new Integer[4];
        arr = list444.toArray(arr);

        for (Integer x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.println("Проверка метода .remove(index): ");
        list1.remove(5);
        System.out.print("Содержание list1 после .remove(index): ");
        System.out.println(list1);

        System.out.println("Проверка метода .remove(Object o): ");
        List<Integer> list5 = new DIYarrayList<>();
        list5.add(10);
        list5.add(20);
        list5.add(20);
        list5.add(30);
        list5.add(40);
        list5.add(20);

        System.out.print("До удаления элемента из list5: ");
        System.out.println(list5);

        list5.remove(new Integer(20));
        System.out.print("После удаления элемента из list5: ");
        System.out.println(list5);

        list5.remove(null);
        System.out.print("После удаления null из list5: ");
        System.out.println(list5);

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
        System.out.println(list5);

        list5.removeAll(list6);
        System.out.println("list5 после .removeAll: ");
        System.out.println(list5);

        System.out.println("Проверка метода .addAll(Collection<? extends T> c): ");
        list5.addAll(list6);
        System.out.println(list5);

        System.out.println("Проверка метода .addAll(int index, Collection<? extends T> c): ");
        List<Integer> list7 = new DIYarrayList<>();
        list7.add(5555);
        list7.add(8888);
        list5.addAll(5, list7);
        System.out.println(list5);

        System.out.print("Проверка метода .set(index, null): ");
        list5.set(2, null);
        System.out.println(list5);
        System.out.print(".indexOf(null) и .lastIndexOf(null): ");
        System.out.print(list5.indexOf(null));
        System.out.println(" и " + list5.lastIndexOf(null));

        System.out.print("Проверка метода Collections.sort(list, Comparator): ");
        list5.remove(2);   // Удаляем null из list5
        Collections.sort(list5, new MyComparator());
        System.out.println(list5);
    }

    static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i2.compareTo(i1);
        }
    }
}
