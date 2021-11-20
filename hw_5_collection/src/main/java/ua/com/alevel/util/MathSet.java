package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathSet<T extends Number & Comparable<T>> {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    private String id;
    private T[] array;
    private int size = 0;
    private int capacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MathSet() {
        capacity = 5;
        array = (T[]) new Number[capacity];
    }

    public MathSet(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Number[this.capacity];
    }

    public MathSet(T[] numbers) {
        capacity = 5;
        array = (T[]) new Number[capacity];
        for (T number : numbers) {
            add(number);
        }
    }

    public MathSet(T[]... numbers) {
        capacity = 5;
        array = (T[]) new Number[capacity];
        for (T[] numberIt : numbers) {
            add(numberIt);
        }
    }

    public MathSet(MathSet numbers) {
        capacity = numbers.capacity;
        array = ((T[]) new Number[capacity]);
        for (int i = 0; i < numbers.size; ++i) {
            add((T) numbers.array[i]);
        }
        size = numbers.size;
    }

    public MathSet(MathSet... numbers) {
        capacity = 5;
        array = (T[]) new Number[capacity];
        for (MathSet<T> numberIt : numbers) {
            if (numberIt != null) {
                for (int i = 0; i < numberIt.size; ++i) {
                    add(numberIt.array[i]);
                }
            }
        }
    }

    public void add(T number) {
        LOGGER_INFO.info("adding number");
        if (findNumber(number)) {
            return;
        }
        if (size == capacity) {
            T[] newArr = (T[]) new Number[capacity + 5];
            System.arraycopy(array, 0, newArr, 0, capacity);
            capacity += 5;
            array = newArr;
        }
        array[size++] = number;
    }

    public void add(T... numbers) {
        LOGGER_INFO.info("adding numbers");
        for (T numberIt : numbers) {
            add(numberIt);
        }
    }

    public boolean findNumber(T number) {
        if (array != null) {
            for (int i = 0; i < size; ++i) {
                if (number == null)
                    return false;
                if (number.equals(array[i]))
                    return true;
            }
        }
        LOGGER_WARN.warn("array is null");
        return false;
    }

    public int getSize() {
        return size;
    }

    public void join(MathSet ms) {
        LOGGER_INFO.info("joining mathSet");
        for (int i = 0; i < ms.size; ++i) {
            add((T) ms.array[i]);
        }
    }

    public void join(MathSet... ms) {
        LOGGER_INFO.info("joining mathSets");
        for (MathSet setIt : ms) {
            for (int i = 0; i < setIt.size; ++i) {
                add((T) setIt.array[i]);
            }
        }
    }

    public void intersection(MathSet ms) {
        LOGGER_INFO.info("finding intersection");
        MathSet<T> result = new MathSet<>();
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < ms.size; ++j) {
                if (this.array[i].doubleValue() == ms.array[j].doubleValue()) {
                    result.add((T) ms.array[j]);
                }
            }
        }
        clear();
        join(new MathSet(result));
    }

    public void intersection(MathSet... ms) {
        LOGGER_INFO.info("finding multiple intersections:");
        MathSet<T> result = new MathSet<>();
        for (MathSet setIt : ms) {
            intersection(setIt);
        }
        LOGGER_INFO.info("done");
    }

    public void sortDesc() {
        LOGGER_INFO.info("sort descending whole array");
        if (size > 0) {
            for (int i = 0; i < size - 1; ++i) {
                for (int j = i + 1; j < size; ++j) {
                    if (array[i].doubleValue() < array[j].doubleValue()) {
                        T tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
        }
    }

    public void sortDesc(int firstIndex, int secondIndex) {
        LOGGER_INFO.info("sort descending from index to index");
        if ((secondIndex - firstIndex) > 0) {
            for (int i = firstIndex; i < secondIndex - 1; ++i) {
                for (int j = i + 1; j < secondIndex; ++j) {
                    if (array[i].doubleValue() < array[j].doubleValue()) {
                        T tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
        }
    }

    public void sortDesc(T number) {
        int i;
        if ((i = getNumberIndex(number)) != -1)
            sortDesc(i, size);
    }

    public void sortAsc() {
        LOGGER_INFO.info("sort ascending whole array");
        if (size > 0) {
            for (int i = 0; i < size - 1; ++i) {
                for (int j = i + 1; j < size; ++j) {
                    if (array[i].doubleValue() > array[j].doubleValue()) {
                        T tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
        }
    }

    public void sortAsc(int firstIndex, int secondIndex) {
        LOGGER_INFO.info("sort ascending from index to index");
        if ((secondIndex - firstIndex) > 0) {
            for (int i = firstIndex; i < secondIndex - 1; ++i) {
                for (int j = i + 1; j < secondIndex; ++j) {
                    if (array[i].doubleValue() > array[j].doubleValue()) {
                        T tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
        }
    }

    public void sortAsc(T number) {
        int i;
        if ((i = getNumberIndex(number)) != -1)
            sortAsc(i, size);
    }

    public Number get(int index) {
        LOGGER_INFO.info("get Number at index");
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index out of bounds");
        return array[index];
    }

    public T getMax() {
        LOGGER_INFO.info("getting max Number");
        T result = array[0];
        for (int i = 0; i < size; ++i) {
            if (array[i].doubleValue() > result.doubleValue()) {
                result = array[i];
            }
        }
        return result;
    }

    public T getMin() {
        LOGGER_INFO.info("getting min Number");
        T result = array[0];
        for (int i = 0; i < size; ++i) {
            if (array[i].doubleValue() < result.doubleValue()) {
                result = array[i];
            }
        }
        return result;
    }

    public Double getAverage() {
        LOGGER_INFO.info("getting average");
        double summa = 0;
        for (int i = 0; i < size; ++i) {
            summa += array[i].doubleValue();
        }
        return summa / size;
    }

    public Number getMedian() {
        LOGGER_INFO.info("getting median");
        if (size > 0) {
            sortAsc();
            if (size % 2 != 0) {
                return array[size / 2];
            } else {
                Double first = array[size / 2].doubleValue();
                Double second = array[size / 2].doubleValue();
                return (first + second) / 2;
            }
        }
        return null;
    }

    public T[] toArray() {
        return array;
    }

    public T[] toArray(int firstIndex, int secondIndex) {
        T[] newArr = (T[]) new Number[(secondIndex - firstIndex) + 1];
        System.arraycopy(array, firstIndex, newArr, 0, secondIndex - firstIndex + 1);
        return newArr;
    }

    public MathSet cut(int firstIndex, int secondIndex) {
        LOGGER_INFO.info("getting cut from set");
        MathSet<T> set = new MathSet<>();
        for (int i = 0; i < capacity; ++i) {
            if (i <= secondIndex && i >= firstIndex)
                set.add(array[i]);
        }
        return set;
    }

    public void clear() {
        LOGGER_INFO.info("clearing");
        for (int i = 0; i < size; ++i) {
            array[i] = null;
        }
        size = 0;
    }

    public void clear(T[] numbers) {
        LOGGER_INFO.info("clearing numbers");
        for (T numberIt : numbers) {
            for (int i = 0; i < size; ++i) {
                if (numberIt.equals(array[i])) {
                    for (int j = i; j < size; ++j) {
                        array[j] = array[j + 1];
                        array[j + 1] = null;
                    }
                    size--;
                }
            }
        }
    }

    public int getNumberIndex(T number) {
        LOGGER_INFO.info("getting number index");
        for (int i = 0; i < size; ++i) {
            if (array[i].doubleValue() == number.doubleValue())
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            if (array[i] != null)
                string.append(" ").append(array[i]);
            if (i != size - 1)
                string.append(",");
        }
        return "MathSet: ['" + id + "', array = [" + string + " ], size = " + size + "]";
    }
}
