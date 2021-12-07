package ua.com.alevel.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.converters.DateToMilliseconds;
import ua.com.alevel.util.converters.MillisecondsToDate;

public class ConverterTest {
    private final DateClass date = new DateClass();

    @Test
    public void shouldBeEqual() {
        date.setValues(2003, 7, 16, 23, 59, 59, 999);

        long tmp = DateToMilliseconds.dateToMillis(date);
        DateClass tmpDate = MillisecondsToDate.millisToDate(tmp);

        //Assertions.assertEquals(date, MillisecondsToDate.millisToDate(tmp)); выдает false хотя то что внизу - true

        Assertions.assertEquals(date.getYear(), tmpDate.getYear());
        Assertions.assertEquals(date.getMonth(), tmpDate.getMonth());
        Assertions.assertEquals(date.getDay(), tmpDate.getDay());
        Assertions.assertEquals(date.getHour(), tmpDate.getHour());
        Assertions.assertEquals(date.getMinute(), tmpDate.getMinute());
        Assertions.assertEquals(date.getSecond(), tmpDate.getSecond());
        Assertions.assertEquals(date.getMillisecond(), tmpDate.getMillisecond());
    }

}
