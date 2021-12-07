package ua.com.alevel.util.converters;

import ua.com.alevel.date.DateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringToDate {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private static final String[] MONTHS = {"January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"};

    public DateClass stringToDate(String date, String choice) {
        DateClass myDate = new DateClass();
        switch (choice) {
            case "1":
                myDate = firstFormat(date);
                break;
            case "2":
                myDate = secondFormat(date);
                break;
            case "3":
                myDate = thirdFormat(date);
                break;
            case "4":
                myDate = fourthFormat(date);
                break;
            default:
                System.out.println("Incorrect input");
        }
        return myDate;
    }

    public static DateClass firstFormat(String stringDate) {
        LOGGER_INFO.info("Converting first format to string");
        DateClass myDate = new DateClass();

        String delimiter;
        if (stringDate.contains("/")) {
            delimiter = "/";
        } else {
            delimiter = "-";
        }

        String[] split = new String[4];
        switch (delimiter) {
            case "/":
                split = stringDate.split("[/ ]", 4);
                break;
            case "-":
                split = stringDate.split("[- ]", 4);
                break;
        }
        try {
            if (!split[0].equals("")) {
                myDate.setDay(Integer.parseInt(split[0]));
            } else {
                myDate.setDay(1);
            }

            if (!split[1].equals("")) {
                myDate.setMonth(Integer.parseInt(split[1]));
            } else {
                myDate.setMonth(1);
            }

            if (!split[2].equals("")) {
                myDate.setYear(Integer.parseInt(split[2]));
            } else {
                myDate.setYear(0);
            }

            if (split.length > 3) {
                setTime(myDate, split[3]);
            }

            return myDate;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            throw new NumberFormatException();
        }
    }

    public static DateClass secondFormat(String stringDate) {
        LOGGER_INFO.info("Converting second format to string");
        DateClass date = new DateClass();

        String punct;

        if (stringDate.contains("/")) {
            punct = "/";
        } else {
            punct = "-";
        }

        String[] split = new String[4];

        switch (punct) {
            case "/":
                split = stringDate.split("[/ ]", 4);
                break;
            case "-":
                split = stringDate.split("[- ]", 4);
                break;
        }

        try {
            if (!split[0].equals("")) {
                date.setMonth(Integer.parseInt(split[0]));
            } else {
                date.setMonth(1);
            }

            if (!split[1].equals("")) {
                date.setDay(Integer.parseInt(split[1]));
            } else {
                date.setDay(1);
            }

            if (!split[2].equals("")) {
                date.setYear(Integer.parseInt(split[2]));
            } else {
                date.setYear(0);
            }

            if (split.length > 3) {
                setTime(date, split[3]);
            }

            return date;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            throw new NumberFormatException();
        }
    }

    public static DateClass thirdFormat(String stringDate) {
        LOGGER_INFO.info("Converting third format to string");
        DateClass date = new DateClass();

        String[] split;
        if (stringDate.contains("-")) {
            split = stringDate.split("[- ]", 4);
        } else {
            split = stringDate.split("[ ]", 4);
        }

        try {
            int month;

            if (!split[0].equals("")) {
                for (int i = 0; i < MONTHS.length; i++) {
                    if (split[0].equals(MONTHS[i])) {
                        month = i + 1;
                        date.setMonth(month);
                    }
                }
            } else {
                date.setMonth(1);
            }

            if (!split[1].equals("")) {
                date.setDay(Integer.parseInt(split[1]));
            } else {
                date.setDay(1);
            }

            if (!split[2].equals("")) {
                date.setYear(Integer.parseInt(split[2]));
            } else {
                date.setYear(0);
            }

            if (split.length > 3) {
                setTime(date, split[3]);
            }

            return date;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            throw new NumberFormatException();
        }
    }

    public static DateClass fourthFormat(String stringDate) {
        LOGGER_INFO.info("Converting fourth format to string");
        DateClass date = new DateClass();

        String[] split;
        if (stringDate.contains("-")) {
            split = stringDate.split("[- ]", 4);
        } else {
            split = stringDate.split("[ ]", 4);
        }

        try {
            if (!split[0].equals("")) {
                date.setDay(Integer.parseInt(split[0]));
            } else {
                date.setDay(1);
            }
            int month;

            if (!split[1].equals("")) {
                for (int i = 0; i < MONTHS.length; i++) {
                    if (split[1].equals(MONTHS[i])) {
                        month = i + 1;
                        date.setMonth(month);
                    }
                }
            } else {
                date.setMonth(1);
            }

            if (!split[2].equals("")) {
                date.setYear(Integer.parseInt(split[2]));
            } else {
                date.setYear(0);
            }

            if (split.length > 3) {
                setTime(date, split[3]);
            }

            return date;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            throw new NumberFormatException();
        }
    }

    public static void setTime(DateClass date, String time) {
        String[] split = time.split(":");

        try {
            for (int i = 0; i < split.length; i++) {
                switch (i) {
                    case 0:
                        date.setHour(Integer.parseInt(split[0]));
                        break;
                    case 1:
                        date.setMinute(Integer.parseInt(split[1]));
                        break;
                    case 2:
                        date.setSecond(Integer.parseInt(split[2]));
                        break;
                    case 3:
                        date.setMillisecond(Integer.parseInt(split[3]));
                        break;
                }
            }
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            throw new NumberFormatException();
        }
    }
}
