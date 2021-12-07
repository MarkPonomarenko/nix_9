package ua.com.alevel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.converters.DateToMilliseconds;

import java.util.ArrayList;

public class DateSort {

    private static final DateToMilliseconds convert = new DateToMilliseconds();
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public static ArrayList<DateClass> sort(ArrayList<DateClass> dates, String type) {
        int size = dates.size();
        boolean flag;
        switch (type) {
            case "1":
                LOGGER_INFO.info("[Sorter] Ascending sort");
                do {
                    flag = true;
                    for (int i = 0; i < size - 1; ++i) {
                        for (int j = i + 1; j < size; ++j) {
                            if (convert.dateToMillis(dates.get(i)) > convert.dateToMillis(dates.get(j))) {
                                DateClass date = dates.get(i);
                                dates.set(i, dates.get(j));
                                dates.set(j, date);
                            }
                        }
                    }
                } while (!flag);
                return dates;
            case "0":
                LOGGER_INFO.info("[Sorter] Descending sort");
                do {
                    flag = true;
                    for (int i = 0; i < size - 1; ++i) {
                        for (int j = i + 1; j < size; ++j) {
                            if (convert.dateToMillis(dates.get(i)) < convert.dateToMillis(dates.get(j))) {
                                DateClass date = dates.get(i);
                                dates.set(i, dates.get(j));
                                dates.set(j, date);
                            }
                        }
                    }
                } while (!flag);
                return dates;
            default:
                System.out.println("Сортировка не была произведена!");
                return dates;
        }
    }
}
