/*
* Новая реализация ArrayList на основе массива
* Если метод не имплементирован, то он выбрасывает исключение UnsupportedOperationException
 */

package com.nuramov.hw02DIYarrayList;

import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private int defaultCapacity = 10;
    private int size = 0;
    private T[] elementData;

    // Конструктор с инициализацией размера массива
    public DIYarrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            elementData = (T[]) new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    // Конструктор с дефолтным размером массива. Размер массива по дефолту равен 10
    public DIYarrayList() {
        elementData = (T[]) new Object[defaultCapacity];
    }

    // Проверка корректности значения index для методов add() и addAll()
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: "+ index);
        }
    }

    // Добавляет все элементы одного списока в конец другого списка (в список-назначения)
    @Override
    public boolean addAll(Collection<? extends T> c) {
        T[] a = (T[]) c.toArray();
        int aLength = a.length;
        if (aLength == 0) return false;
        if (aLength > (elementData.length - size)) {
            elementData = Arrays.copyOf(elementData, size + aLength + 1);  // +1 к размеру массива (в запас)
        }
        System.arraycopy(a, 0, elementData, size, aLength);
        size = size + aLength;
        return true;
    }

    // Добавляет все элементы одного списока в заданный индекс другого списка (в список-назначения)
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheckForAdd(index);

        T[] a = (T[]) c.toArray();
        int aLength = a.length;
        if (aLength == 0) return false;
        if (aLength > (elementData.length - size)) {
            elementData = Arrays.copyOf(elementData, size + aLength + 1);  // +1 к размеру массива (в запас)
        }
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + aLength, numMoved);
        }
        System.arraycopy(a, 0, elementData, index, aLength);
        size = size + aLength;
        return true;
    }

    // Возвращает количество элементов в списке
    @Override
    public int size() {
        return size;
    }

    // Проверяет список на наличие элементов (пустой или нет)
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Добавляет единичный элемент в список
    @Override
    public boolean add(T e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length + 1);
        }
        elementData[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        rangeCheckForAdd(index);

        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length + 1);
        }
        System.arraycopy(elementData, index, elementData,index + 1, size - index);
        elementData[index] = element;
        size++;
    }



    // Ниже методы, которые необходимо переопределить или выбросить исключение UnsupportedOperationException

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void sort(Comparator<? super T> c) {

    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
