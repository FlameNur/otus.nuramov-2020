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
    @SuppressWarnings("unchecked")
    public DIYarrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            elementData = (T[]) new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    // Конструктор с дефолтным размером массива. Размер массива по дефолту равен 10
    @SuppressWarnings("unchecked")
    public DIYarrayList() {
        elementData = (T[]) new Object[defaultCapacity];
    }

    // Проверка корректности значения index для методов add() и addAll()
    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: "+ index);
        }
    }

    // Добавляет все элементы одного списока в конец другого списка (в список-назначения)
    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);

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
    public int size() { return size; }

    // Проверяет список на наличие элементов (пустой или нет)
    @Override
    public boolean isEmpty() { return size == 0; }

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

    // Добавляет единичный элемент в список по индексу
    @Override
    public void add(int index, T element) {
        checkIndex(index);

        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length + 1);
        }
        System.arraycopy(elementData, index, elementData,index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    // Позволяет получить элемент списка по индексу
    @Override
    public T get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    // Сортирует содержание списка (без этого метода не работает метод Collections.sort())
    public void sort(Comparator<? super T> c) {
        Arrays.sort(elementData, 0, size, c);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int nextElement = 0;

            @Override
            public boolean hasNext() {
                return nextElement < size && elementData[nextElement] != null;
            }

            @Override
            public T next() {
                nextElement++;
                return elementData[nextElement - 1];
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<>() {
            private int nextElement = 0;
            private int previousElement = -1;

            @Override
            public boolean hasNext() {
                return nextElement < size && elementData[nextElement] != null;
            }

            @Override
            public T next() {
                nextElement++;
                previousElement++;
                return elementData[nextElement - 1];
            }

            @Override
            public boolean hasPrevious() {
                return previousElement >= 0 && previousElement < size && elementData[previousElement] != null;
            }

            @Override
            public T previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {

            }
        };
    }







    // Не поддерживается
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    // Не поддерживается
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    // Не поддерживается
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    // Не поддерживается
    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }



    // Ниже методы, которые необходимо переопределить или выбросить исключение UnsupportedOperationException



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
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

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

}
