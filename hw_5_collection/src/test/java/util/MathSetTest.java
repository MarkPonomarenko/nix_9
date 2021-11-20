package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ua.com.alevel.util.MathSet;

public class MathSetTest {

    private static MathSet<Integer> set;

    @Test
    @Order(1)
    public void shouldTestConstructorsAndAdd() {
        set = new MathSet<>(new Integer[]{5, 6, 2, 8}, new Integer[]{2, 4, 7});
        MathSet<Integer> set1 = new MathSet<>(set);
        set1.add(10);
        set1.add(47);
        MathSet<Integer> set2 = new MathSet<>(set, set1);

        Assertions.assertEquals(6, set.getSize());
        Assertions.assertEquals(8, set1.getSize());
        Assertions.assertEquals(8, set2.getSize());
    }

    @Test
    @Order(2)
    public void shouldTestSorting() {
        set = new MathSet<>(new Integer[]{5, 6, 2, 8}, new Integer[]{2, 4, 7});
        set.sortDesc();
        Assertions.assertEquals(8, set.get(0));
        set.sortAsc();
        Assertions.assertEquals(2, set.get(0));
        set.sortDesc(4);
        Assertions.assertEquals(8, set.get(1));
        set.sortAsc(1, set.getSize());
        Assertions.assertEquals(8, set.get(set.getSize() - 1));
    }

    @Test
    @Order(3)
    public void shouldTestIntersection() {
        set = new MathSet<>(new Integer[]{5, 6, 2, 8}, new Integer[]{2, 4, 7});
        MathSet<Integer> set1 = new MathSet<>(new Integer[]{6, 4, 9});
        set.intersection(set1);
        Assertions.assertEquals(2, set.getSize());
        MathSet<Integer> set2 = new MathSet<>(new Integer[]{6, 7, 12});
        MathSet<Integer> set3 = new MathSet<>(new Integer[]{20, 6, 12});
        set.intersection(set2, set3);
        Assertions.assertEquals(1, set.getSize());
        Assertions.assertEquals(6, set.get(0));
    }

    @Test
    @Order(4)
    public void shouldTestJoin() {
        set = new MathSet<>(new Integer[]{5, 6, 2, 8}, new Integer[]{2, 4, 7});
        MathSet<Integer> set1 = new MathSet<>(new Integer[]{6, 3, 10});
        set.join(set1);
        Assertions.assertEquals(8, set.getSize());
        MathSet<Integer> set2 = new MathSet<>(new Integer[]{1, 2, 11});
        MathSet<Integer> set3 = new MathSet<>(new Integer[]{12, 13, 14});
        set.join(set2, set3);
        Assertions.assertEquals(13, set.getSize());
    }

    @Test
    @Order(5)
    public void shouldTestGetValues() {
        set = new MathSet<>(new Integer[]{10, 6, 2, 8}, new Integer[]{2, 4, 12});
        Assertions.assertEquals(8.0, set.getMedian());
        Assertions.assertEquals(7.0, set.getAverage());
        Assertions.assertEquals(2, set.get(0)); //2 - бо для поиска медианы сет сортируется
        Assertions.assertEquals(2, set.getMin());
        Assertions.assertEquals(12, set.getMax());
    }

    @Test
    @Order(6)
    public void shouldTestCut() {
        set = new MathSet<>(new Integer[]{5, 6, 2, 8}, new Integer[]{2, 4, 7});
        Assertions.assertEquals(4, set.cut(0, 3).getSize());
        Assertions.assertEquals(6, set.getSize());
        set.clear(new Integer[]{4, 7});
        Assertions.assertEquals(4, set.getSize());
        set.clear();
        Assertions.assertEquals(0, set.getSize());
    }
}
