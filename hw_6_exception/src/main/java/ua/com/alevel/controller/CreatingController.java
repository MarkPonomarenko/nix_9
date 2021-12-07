package ua.com.alevel.controller;

import ua.com.alevel.check.CheckFormat;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.DateSort;
import ua.com.alevel.util.converters.DateToString;
import ua.com.alevel.util.converters.StringToDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class CreatingController {
    private static final StringToDate CONVERT = new StringToDate();
    private static final DateToString PRINT_DATE = new DateToString();

    public static void run() throws NumberFormatException {
        ArrayList<DateClass> dates = new ArrayList<>();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String format = null;
        try {
            do {
                System.out.println("Выберите формат:");
                System.out.println("1. dd/mm/yy - 00:00:00:000");
                System.out.println("2. m/d/yyyy - 00:00:00:000");
                System.out.println("3. mmm-d-yy - 00:00:00:000");
                System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
                format = input.readLine();
            } while (Integer.parseInt(format) > 4 || Integer.parseInt(format) <= 0);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            System.out.print("Кол-во дат:");
            String count = input.readLine();
            for (int i = 0; i < Integer.parseInt(count); i++) {
                System.out.print("Введите дату:");
                String date;

                boolean isValid = true;
                do {
                    if (!isValid) {
                        System.out.println("Неверный ввод");
                    }
                    date = input.readLine();
                    isValid = false;
                } while (CheckFormat.check(date, Objects.requireNonNull(format)) || CheckFormat.checkFormatOfTime(date));

                DateClass myDate = null;
                try {
                    myDate = CONVERT.stringToDate(date, format);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dates.add(myDate);
            }

            System.out.println("1 - Сортировка по возрастанию");
            System.out.println("0 - Сортировка по убыванию");
            String choiceSort = input.readLine();
            ArrayList<DateClass> sortedDates = DateSort.sort(dates, choiceSort);

            System.out.println("Выберите формат:");
            System.out.println("1. dd/mm/yy - 00:00:00:000");
            System.out.println("2. m/d/yyyy - 00:00:00:000");
            System.out.println("3. mmm-d-yy - 00:00:00:000");
            System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
            String choiceFormat = input.readLine();
            System.out.println("Даты:");
            for (DateClass d : sortedDates) {
                System.out.print(PRINT_DATE.dateToString(d, choiceFormat));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage() + " Incorrect!");
        }
    }
}
