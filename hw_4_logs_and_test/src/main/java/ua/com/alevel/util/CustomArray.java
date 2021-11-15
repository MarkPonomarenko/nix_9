package ua.com.alevel.util;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CustomArray<T> {

    private int size = 0;
    private int length;
    private T[] array;

    @SuppressWarnings("unchecked")
    public CustomArray(int size) {
        length = 1;
        while (length < size) {
            length *= 2;
        }
        array = (T[]) new Object[size];
    }

    public CustomArray() {
        this(1);
    }

    public int size() {
        return size;
    }

    public T getAt(int index) {
        if (indexCheck(index))
            return array[index];
        else
            throw new IndexOutOfBoundsException();
    }

    private boolean indexCheck(int index) {
        return index >= 0 && index <= size;
    }

    public void add(T entity) {
        add(entity, size);
    }

    public void add(T entity, int index) {
        T tmp;
        if (!indexCheck(index))
            throw new IndexOutOfBoundsException();
        if (size == array.length)
            resize();
        if (index < size) {
            for (int i = size - 1; i >= index; --i) {
                tmp = array[i];
                array[i + 1] = tmp;
            }
        }
        array[index] = entity;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    private void resize() {
        T[] newArr;
        if (length >= 0) {
            newArr = Arrays.copyOf(array, array.length + 1);
            this.array = newArr;
            this.length = array.length;
        }
    }

    public void setAt(T entity, int index) {
        T prev = array[index];
        checkIndex(index);
        array[index] = entity;
    }

    public void removeAt(int index) {
        if (!indexCheck(index))
            throw new IndexOutOfBoundsException();
        if (index < size) {
            int it = index;
            array[it] = null;
            while (it < size - 1) {
                array[it] = array[it + 1];
                it++;
            }
        }
        size--;
    }

    @Override
    public String toString() {
        if (array == null)
            return "Пусто";
        StringBuilder strOut = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            if (i != size - 1)
                strOut.append(array[i]).append(", ");
            else
                strOut.append(array[i]);
        }
        return "[" + strOut + "]";
    }

    public boolean isEmpty() {
        for (T entity : array) {
            if (entity != null)
                return false;
        }
        return true;
    }

    public void flush() {
        for (int i = 0; i < length; ++i) {
            array[i] = null;
        }
        size = 0;
        length = 1;
        array = (T[]) new Object[size];
    }
}
