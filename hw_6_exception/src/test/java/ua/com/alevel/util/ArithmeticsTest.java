package ua.com.alevel.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.converters.DateToMilliseconds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArithmeticsTest {

    public static final DateClass dateFirst = new DateClass();
    public static final DateClass dateSecond = new DateClass();

    @Test
    public void shouldAddAndSubtract() {
        dateFirst.setValues(2003, 7, 16, 2, 10, 34, 500);
        long millis = DateToMilliseconds.minutesToMillis(dateFirst.getMinute());

        DateClass tmpDate = Arithmetics.addMilli(dateFirst, millis);
        Assertions.assertEquals(20, tmpDate.getMinute());
        Assertions.assertEquals(10, (Arithmetics.subtractMillis(tmpDate, millis)).getMinute());
    }

    @Test
    public void shouldFindDifference() throws ParseException {
        dateSecond.setValues(2003, 7, 16, 2, 10, 34, 300);

        long difference = Arithmetics.difference(dateFirst, dateSecond);
        Assertions.assertEquals(200, difference);

        dateSecond.setValues(2003,7,16,0,0,0,0);
        dateFirst.setValues(2003,7,18,0,0,0,0);
        difference = Arithmetics.difference(dateFirst, dateSecond);


        Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse("16.07.2003");
        Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse("18.07.2003");

        long millis = date2.getTime() - date1.getTime();
        Assertions.assertEquals(difference, millis);
    }
}
