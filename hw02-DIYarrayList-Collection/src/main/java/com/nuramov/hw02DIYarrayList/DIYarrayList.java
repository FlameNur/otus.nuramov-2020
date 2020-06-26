/*
* Новая реализация ArrayList на основе массива
* Если метод не имплементирован, то он выбрасывает исключение UnsupportedOperationException
 */

package com.nuramov.hw02DIYarrayList;

import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
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
        elementData = (T[]) new Object[DEFAULT_CAPACITY];
    }

    // Проверка корректности значения index для методов add() и addAll()
    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Illegal value");
        }
    }

    // Увеличение размера списка для методов add
    private void sizeGrowingForAdd() {
        int sizeGrowing = elementData.length * 2 + 1;
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length + sizeGrowing);
        }
    }

    // Увеличение размера списка для методов addAll
    private void sizeGrowingForAddAll(T[] a) {
        int aLength = a.length;
        int sizeGrowing = elementData.length * 2 + 1;
        if (aLength > (elementData.length - size)) {
            elementData = Arrays.copyOf(elementData, size + aLength + sizeGrowing);
        }
    }

    // Добавляет все элементы одного списока в конец другого списка (в список-назначения)
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends T> c) {
        T[] a = (T[]) c.toArray();
        if (a.length == 0) return false;
        sizeGrowingForAddAll(a);

        System.arraycopy(a, 0, elementData, size, a.length);
        size = size + a.length;
        return true;
    }

    // Добавляет все элементы одного списока в заданный индекс другого списка (в список-назначения)
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        T[] a = (T[]) c.toArray();
        if (a.length == 0) return false;
        sizeGrowingForAddAll(a);

        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + a.length, numMoved);
        }
        System.arraycopy(a, 0, elementData, index, a.length);
        size = size + a.length;
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
        sizeGrowingForAdd();
        elementData[size] = e;
        size++;
        return true;
    }

    // Добавляет единичный элемент в список по индексу
    @Override
    public void add(int index, T element) {
        checkIndex(index);
        sizeGrowingForAdd();
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
            private int currentElement = -1;

            @Override
            public boolean hasNext() {
                return nextElement < size && elementData[nextElement] != null;
            }

            @Override
            public T next() {
                nextElement++;
                previousElement++;
                currentElement = previousElement;
                return elementData[currentElement];
            }

            @Override
            public boolean hasPrevious() {
                return previousElement >= 0 && previousElement < size && elementData[previousElement] != null;
            }

            @Override
            public T previous() {
                nextElement--;
                previousElement--;
                currentElement = nextElement;
                return elementData[currentElement];
            }

            @Override
            public int nextIndex() {
                return nextElement;
            }

            @Override
            public int previousIndex() {
                return previousElement;
            }

            @Override
            public void remove() {
                DIYarrayList.this.remove(currentElement);
            }

            @Override
            public void set(T t) {
                DIYarrayList.this.set(currentElement, t);
            }

            @Override
            public void add(T t) {
                DIYarrayList.this.add(currentElement, t);
            }
        };
    }

    // Устанавливает новый элемент в требуемый индекс списка
    @Override
    public T set(int index, T element) {
        checkIndex(index);
        return elementData[index] = element;
    }

    // Удаляет элемент из списка по индексу
    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement = elementData[index];
        System.arraycopy(elementData, index + 1, elementData,index, size - index - 1);
        elementData = Arrays.copyOf(elementData, elementData.length - 1);
        size--;
        return removedElement;
    }

    // Об'null'яет элементы списка
    @Override
    public void clear() {
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                elementData[i] = null;
            }
            size = 0;
        }
    }

    // Переводит список в массив
    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(elementData, 0, size);
    }

    // Переводит список в заданный массив
    @Override
    @SuppressWarnings("unchecked")
    public <R> R[] toArray(R[] a) {
        if (a.length < size) {
            return (R[]) Arrays.copyOfRange(elementData, 0, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    // Удаляет заданный первый объект в списке
    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i >= 0) {
            remove(i);
            return true;
        } else return false;
    }

    // Возвращает индекс требуемого элемента
    @Override
    public int indexOf(Object o) {
        if(o == null) {
            for (int i = 0; i < size; i++) {
                if(elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if(o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Возвращает true, если элемент имеется в списке
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // Возвращает последний индекс требуемого элемента, если требуемый элемент встречается несколько раз
    @Override
    public int lastIndexOf(Object o) {
        if(o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if(elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if(o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Возвращает true, если элементы коллекции имеются в списке
    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o: c) {
            if(!contains(o)) {
                return false;
            }
        }
        return true;
    }

    // Удаляет все объекты, соотвествующие заданному
    private boolean removeAllSameObjects(Object o) {
        boolean b = false;
        for (int i = 0; i < size; i++) {
            if(o.equals(elementData[i])) {
                remove(i);
                i--;
                b = true;
            }
        }
        return b;
    }

    // Возвращает true, если удалил все элементы коллекции в списке
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean elementsAreRemoved = false;
        for (Object o : c) {
            if(removeAllSameObjects(o)) {
                elementsAreRemoved = true;
            }
        }
        return elementsAreRemoved;
    }

    @Override
    public String toString() {
        String str = "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            result.append(get(i)).append(" ");
        }
        str = result.toString();
        return str;
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

    // Не поддерживается
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
