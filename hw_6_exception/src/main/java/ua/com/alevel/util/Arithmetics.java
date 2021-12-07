package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.converters.DateToMilliseconds;
import ua.com.alevel.util.converters.MillisecondsToDate;

public class Arithmetics {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public static long difference(DateClass first, DateClass second) {
        LOGGER_INFO.info("[Arithmetics] Finding difference");
        long firstMillis = DateToMilliseconds.dateToMillis(first);
        long secondMillis = DateToMilliseconds.dateToMillis(second);
        return Math.abs(firstMillis - secondMillis);
    }

    public static DateClass addMilli(DateClass date, long millis) {
        LOGGER_INFO.info("[Arithmetics] Adding milliseconds");
        long dateMillis = DateToMilliseconds.dateToMillis(date);
        return MillisecondsToDate.millisToDate(dateMillis + millis);
    }

    public static DateClass subtractMillis(DateClass date, long millis) {
        LOGGER_INFO.info("[Arithmetics] Subtracting milliseconds");
        long dateMillis = DateToMilliseconds.dateToMillis(date);
        return MillisecondsToDate.millisToDate(dateMillis - millis);
    }
}
