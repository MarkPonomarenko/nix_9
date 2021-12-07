package ua.com.alevel.util.converters;

import ua.com.alevel.date.DateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateToString {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    private final String[] MONTHS = {"January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"};

    public String dateToString(DateClass date, String choice) {
        String dateStr = "";

        switch (choice) {
            case "1":
                System.out.println(firstFormat(date, dateStr));
                break;
            case "2":
                System.out.println(secondFormat(date, dateStr));
                break;
            case "3":
                System.out.println(thirdFormat(date, dateStr));
                break;
            case "4":
                System.out.println(fourthFormat(date, dateStr));
                break;
            default:
                System.out.println("Неверный ввод!");
        }
        return dateStr;
    }

    private String firstFormat(DateClass date, String dateStr) {
        LOGGER_INFO.info("[DateFormatter] Creating first format of date");
        if (date.getDay() == 0) {
            dateStr += "1";
        }
        dateStr += fillingDoubleO(date.getDay()) + "/";
        for (int i = 0; i < MONTHS.length; ++i) {
            if (i == (date.getMonth() - 1)) {
                dateStr += fillingDoubleO(date.getMonth()) + "/";
            }
        }

        dateStr += date.getYear() + " ";

        dateStr += fillingDoubleO(date.getHour()) + ":";
        dateStr += fillingDoubleO(date.getMinute()) + ":";
        dateStr += fillingDoubleO(date.getSecond()) + ":";
        dateStr += fillingTripleO(date.getMillisecond());

        return dateStr;
    }

    private String secondFormat(DateClass date, String dateStr) {
        LOGGER_INFO.info("[DateFormatter] Creating second format of date");

        if (date.getDay() == 0) {
            dateStr += "1";
        }

        for (int i = 0; i < MONTHS.length; ++i) {
            if (i == (date.getMonth() - 1)) {
                dateStr += date.getMonth() + "/";
            }
        }

        dateStr += date.getDay() + "/";
        dateStr += date.getYear() + " ";

        dateStr += fillingDoubleO(date.getHour()) + ":";
        dateStr += fillingDoubleO(date.getMinute()) + ":";
        dateStr += fillingDoubleO(date.getSecond()) + ":";
        dateStr += fillingTripleO(date.getMillisecond());

        return dateStr;
    }

    private String thirdFormat(DateClass date, String dateStr) {
        LOGGER_INFO.info("[DateFormatter] Creating third format of date");

        for (int i = 0; i < MONTHS.length; ++i) {
            if (i == (date.getMonth() - 1)) {
                dateStr += MONTHS[i] + " ";
            }
        }

        if (date.getDay() == 0) {
            dateStr += "1";
        }
        dateStr += date.getDay() + " ";
        dateStr += date.getYear() + " ";

        dateStr += fillingDoubleO(date.getHour()) + ":";
        dateStr += fillingDoubleO(date.getMinute()) + ":";
        dateStr += fillingDoubleO(date.getSecond()) + ":";
        dateStr += fillingTripleO(date.getMillisecond());

        return dateStr;
    }

    private String fourthFormat(DateClass date, String dateStr) {
        LOGGER_INFO.info("[DateFormatter] Creating fourth format of date");

        if (date.getDay() == 0) {
            dateStr += "1";
        }
        dateStr += fillingDoubleO(date.getDay()) + " ";

        for (int i = 0; i < MONTHS.length; ++i) {
            if (i == (date.getMonth() - 1)) {
                dateStr += MONTHS[i] + " ";
            }
        }
        dateStr += date.getYear() + " ";

        dateStr += fillingDoubleO(date.getHour()) + ":";
        dateStr += fillingDoubleO(date.getMinute()) + ":";
        dateStr += fillingDoubleO(date.getSecond()) + ":";
        dateStr += fillingTripleO(date.getMillisecond());

        return dateStr;
    }

    private String fillingTripleO(int millis) {

        if (millis < 10) {
            return "00" + millis;
        }
        if (millis < 100) {
            return "0" + millis;
        }
        return millis + "";
    }

    private String fillingDoubleO(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return value + "";
    }
}
