package ua.com.alevel.db;

import lombok.ToString;
import ua.com.alevel.util.MathSet;

import java.util.Objects;
import java.util.UUID;

@ToString
public class MSStorage {

    private MathSet[] mathSets;
    private int size;
    private int capacity;

    public MSStorage() {
        mathSets = new MathSet[5];
        size = 0;
        capacity = 5;
    }

    public void addSet(MathSet ms) {
        if (size == capacity) {
            MathSet[] newArr = new MathSet[capacity + 5];
            System.arraycopy(mathSets, 0, newArr, 0, capacity + 5);
            mathSets = newArr;
            capacity += 5;
        }
        ms.setId(generateId());
        mathSets[size++] = ms;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; ++i) {
            mathSets[i] = null;
        }
        size = 0;
    }

    public MathSet getAt(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("MathSet Storage index out");
        }
        return mathSets[index];
    }

    public MathSet findById(String id) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(mathSets[i].getId(), id))
                return mathSets[i];
        }
        return null;
    }

    public MathSet[] findAll() {
        return mathSets;
    }

    public String generateId() {
        String id = UUID.randomUUID().toString();
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(id, mathSets[i].getId())) {
                return generateId();
            }
        }
        return id;
    }
}
