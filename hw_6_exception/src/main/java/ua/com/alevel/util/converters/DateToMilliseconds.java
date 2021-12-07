package ua.com.alevel.util.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.enums.MillisInElement;

public class DateToMilliseconds {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");


    public static long secondsToMillis(int s) {
        LOGGER_INFO.info("[Converter] Seconds to millis");
        return s * MillisInElement.SECOND.getMilli();
    }

    public static long minutesToMillis(int m) {
        LOGGER_INFO.info("[Converter] Minutes to millis");
        return m * MillisInElement.MINUTE.getMilli();
    }

    public static long hoursToMillis(int h) {
        LOGGER_INFO.info("[Converter] Hours to millis");
        return h * MillisInElement.HOUR.getMilli();
    }

    public static long daysToMillis(int d) {
        LOGGER_INFO.info("[Converter] Days to millis");
        return d * MillisInElement.DAY.getMilli();
    }

    public static long monthToMillis(int m, int y) {
        LOGGER_INFO.info("[Converter] Months to millis");
        if (m == 0)
            return 0;
        long millis = 0;
        m--;
        switch (m) {
            case 2:
                if (DateClass.leapYear(y)) {
                    millis += daysToMillis(29);
                } else {
                    millis += daysToMillis(28);
                }
                millis += monthToMillis(m, y);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                //выполняется когда месяц 4, 6, 9, 11
                millis += daysToMillis(30);
                millis += monthToMillis(m, y);
                break;
            case 0:
                break;
            default:
                millis += daysToMillis(31);
                millis += monthToMillis(m, y);
        }
        return millis;
    }

    public static long yearToMillis(int y) {
        LOGGER_INFO.info("[Converter] Years to millis");
        int leaps = y / 4 - (y / 100) + y / 400;
        int defaults = y - leaps;

        long leapMillis = MillisInElement.LEAP.getMilli() * leaps;
        long defaultMillis = MillisInElement.YEAR.getMilli() * defaults;

        return defaultMillis + leapMillis;
    }

    public static long dateToMillis(DateClass date) {
        LOGGER_INFO.info("[Converter] Date to millis");
        long millis = 0;

        millis += yearToMillis(date.getYear());
        millis += monthToMillis(date.getMonth(), date.getYear());
        millis += daysToMillis(date.getDay());
        millis += hoursToMillis(date.getHour());
        millis += minutesToMillis(date.getMinute());
        millis += secondsToMillis(date.getSecond());
        millis += date.getMillisecond();

        return millis;
    }
}
