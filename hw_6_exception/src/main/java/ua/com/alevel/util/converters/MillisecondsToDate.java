package ua.com.alevel.util.converters;

import ua.com.alevel.date.DateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.util.enums.ElementInMillis;

public class MillisecondsToDate {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    private static final int[] month_days = DateClass.getDays();

    public static double millisToSeconds(long millis) {
        LOGGER_INFO.info("[Converter] Millis to seconds");
        return millis * ElementInMillis.SECOND.getValue();
    }

    public static double millisToMinutes(long millis) {
        LOGGER_INFO.info("[Converter] Millis to minutes");
        return millis * ElementInMillis.MINUTE.getValue();
    }

    public static double millisToHours(long millis) {
        LOGGER_INFO.info("[Converter] Millis to hours");
        return millis * ElementInMillis.HOUR.getValue();
    }

    public static double millisToDays(long millis) {
        LOGGER_INFO.info("[Converter] Millis to days");
        return millis * ElementInMillis.DAY.getValue();
    }

    public static double millisToYears(long millis) {
        LOGGER_INFO.info("[Converter] Millis to years");
        double result = 0;
        int it = 1;
        while (true) {
            if (millis == 0)
                return result;
            if (DateClass.leapYear(it)) {
                if (millis >= ElementInMillis.LEAP.getValue()) {
                    millis -= ElementInMillis.LEAP.getValue();
                    result++;
                } else {
                    result += millisToDays(millis) / 366;
                    millis = 0;
                }
            } else {
                if (millis >= ElementInMillis.YEAR.getValue()) {
                    millis -= ElementInMillis.YEAR.getValue();
                    result++;
                } else {
                    result += millisToDays(millis) / 365;
                    millis = 0;
                }
            }
            it++;
        }
    }

    public static double millisToMonths(long millis) {
        LOGGER_INFO.info("[Converter] Millis to months");
        double result = 0;
        int it = 0;
        while (true) {
            if (millis == 0)
                return result;
            if (millis >= month_days[it]) {
                millis -= DateToMilliseconds.daysToMillis(month_days[it]);
                result++;
            } else {
                result += millisToDays(millis) / month_days[it];
                millis = 0;
            }
            it++;
        }
    }

    public static DateClass millisToDate(long millis) {
        LOGGER_INFO.info("[Converter] Millis to date");
        DateClass date = new DateClass();

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        int month = 0;
        int year = 0;

        int monthIt = 0;
        long tmpD = days;
        while (tmpD > month_days[monthIt]) {
            tmpD -= month_days[monthIt];
            if (monthIt == 1) {
                if (DateClass.leapYear(year)) {
                    tmpD -= 1;
                }
            }
            monthIt++;
            if (monthIt > 11) {
                year++;
                monthIt = 0;
            }
            month++;
        }
        date.setMillisecond((int) (millis % 1000));
        date.setSecond((int) (seconds % 60));
        date.setMinute((int) (minutes % 60));
        date.setHour((int) (hours % 24));
        if (DateClass.leapYear(year)) {
            date.setDay((int) ((tmpD) % month_days[monthIt]));
        } else {
            date.setDay((int) ((tmpD) % month_days[monthIt]) + 1);
        }
        date.setMonth(((month) % 12) + 1);
        date.setYear(year);
        return date;
    }
}
