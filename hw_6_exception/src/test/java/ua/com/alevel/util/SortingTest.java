package ua.com.alevel.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.alevel.date.DateClass;

import java.util.ArrayList;

public class SortingTest {

    @Test
    public void shouldSort(){

        DateClass dateFirst = new DateClass();
        DateClass dateSecond = new DateClass();
        DateClass dateThird = new DateClass();
        ArrayList<DateClass> arr = new ArrayList<>();

        dateFirst.setValues(2014, 11,29,13,14,56,334);
        dateSecond.setValues(2015, 1,23,23,17,57,452);
        dateThird.setValues(2013, 9,20,7,4,30,100);

        arr.add(dateFirst);
        arr.add(dateSecond);
        arr.add(dateThird);

        DateSort.sort(arr, "1");
        System.out.println(arr);
        Assertions.assertEquals(dateSecond.getYear(), arr.get(2).getYear());

        DateSort.sort(arr, "0");
        System.out.println(arr);
        Assertions.assertEquals(dateThird.getYear(), arr.get(2).getYear());
    }
}
