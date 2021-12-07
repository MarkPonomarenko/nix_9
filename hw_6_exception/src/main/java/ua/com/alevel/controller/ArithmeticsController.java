package ua.com.alevel.controller;

import ua.com.alevel.check.CheckFormat;
import ua.com.alevel.date.DateClass;
import ua.com.alevel.util.Arithmetics;
import ua.com.alevel.util.converters.DateToMilliseconds;
import ua.com.alevel.util.converters.DateToString;
import ua.com.alevel.util.converters.MillisecondsToDate;
import ua.com.alevel.util.converters.StringToDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ArithmeticsController {

    private static final StringToDate convert = new StringToDate();
    private static final DateToString datePrint = new DateToString();

    public static void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        try {
            operations();
            while ((choice = input.readLine()) != null) {
                if (choice.equals("0")) {
                    return;
                }
                options(choice);
                choice = input.readLine();

            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void options(String choice) {
        switch (choice) {
            case "1":
                addition();
                break;
            case "2":
                subtraction();
                break;
            case "3":
                difference();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid input!");
        }
        operations();
    }

    public static void subtraction() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String formatType;
        try {
            do {
                System.out.println("Выберите формат:");
                System.out.println("1. dd/mm/yy - 00:00:00:000");
                System.out.println("2. m/d/yyyy - 00:00:00:000");
                System.out.println("3. mmm-d-yy - 00:00:00:000");
                System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
                formatType = input.readLine();
            } while (Integer.parseInt(formatType) > 4 || Integer.parseInt(formatType) <= 0);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Введите дату:");
        String date;
        boolean isValid = true;
        try {
            do {
                if (!isValid) {
                    System.out.println("Неверный ввод!");
                }
                date = input.readLine();
                isValid = false;
            } while (CheckFormat.check(date, formatType) || CheckFormat.checkFormatOfTime(date));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        DateClass myDate = null;
        try {
            myDate = convert.stringToDate(date, formatType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            boolean flag = true;
            while (flag) {
                System.out.println();
                System.out.println("Выберите тип:");
                inputType();
                long mil;
                String choice = input.readLine();
                switch (choice) {
                    case "1":
                        System.out.print("Введите кол-во:");
                        String years = input.readLine();
                        mil = DateToMilliseconds.yearToMillis(Integer.parseInt(years));
                        myDate = Arithmetics.subtractMillis(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "2":
                        System.out.print("Введите кол-во:");
                        String days = input.readLine();
                        mil = DateToMilliseconds.daysToMillis(Integer.parseInt(days));
                        myDate = Arithmetics.subtractMillis(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "3":
                        System.out.print("Введите кол-во:");
                        String hours = input.readLine();
                        mil = DateToMilliseconds.hoursToMillis(Integer.parseInt(hours));
                        myDate = Arithmetics.subtractMillis(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "4":
                        System.out.print("Введите кол-во:");
                        String minutes = input.readLine();
                        mil = DateToMilliseconds.minutesToMillis(Integer.parseInt(minutes));
                        myDate = Arithmetics.subtractMillis(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "5":
                        System.out.print("Введите кол-во:");
                        String seconds = input.readLine();
                        mil = DateToMilliseconds.secondsToMillis(Integer.parseInt(seconds));
                        myDate = Arithmetics.subtractMillis(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "6":
                        System.out.print("Введите кол-во:");
                        String milliseconds = input.readLine();
                        myDate = Arithmetics.subtractMillis(myDate, Integer.parseInt(milliseconds));
                        printDateFormat(myDate, input);
                        break;
                    case "0":
                        flag = false;
                        break;
                    default:
                        System.out.println("Неверный ввод!");
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addition() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String formatType;
        try {
            do {
                System.out.println("Выберите формат:");
                System.out.println("1. dd/mm/yy - 00:00:00:000");
                System.out.println("2. m/d/yyyy - 00:00:00:000");
                System.out.println("3. mmm-d-yy - 00:00:00:000");
                System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
                formatType = input.readLine();
            } while (Integer.parseInt(formatType) > 4 || Integer.parseInt(formatType) <= 0);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Введите дату:");
        String date;
        boolean isValid = true;
        try {
            do {
                if (!isValid) {
                    System.out.println("Incorrect input, try again");
                }
                date = input.readLine();
                isValid = false;
            } while (CheckFormat.check(date, formatType) || CheckFormat.checkFormatOfTime(date));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        DateClass myDate = null;
        try {
            myDate = convert.stringToDate(date, formatType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            boolean flag = true;
            while (flag) {
                System.out.println();
                System.out.println("Выберите тип:");
                inputType();
                long mil;
                String choice = input.readLine();
                switch (choice) {
                    case "1":
                        System.out.print("Введите кол-во:");
                        String years = input.readLine();
                        mil = DateToMilliseconds.yearToMillis(Integer.parseInt(years));
                        myDate = Arithmetics.addMilli(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "2":
                        System.out.print("Введите кол-во:");
                        String days = input.readLine();
                        mil = DateToMilliseconds.daysToMillis(Integer.parseInt(days));
                        myDate = Arithmetics.addMilli(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "3":
                        System.out.print("Введите кол-во:");
                        String hours = input.readLine();
                        mil = DateToMilliseconds.hoursToMillis(Integer.parseInt(hours));
                        myDate = Arithmetics.addMilli(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "4":
                        System.out.print("Введите кол-во:");
                        String minutes = input.readLine();
                        mil = DateToMilliseconds.minutesToMillis(Integer.parseInt(minutes));
                        myDate = Arithmetics.addMilli(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "5":
                        System.out.print("Введите кол-во:");
                        String seconds = input.readLine();
                        mil = DateToMilliseconds.secondsToMillis(Integer.parseInt(seconds));
                        myDate = Arithmetics.addMilli(myDate, mil);
                        printDateFormat(myDate, input);
                        break;
                    case "6":
                        System.out.print("Введите кол-во:");
                        String milliseconds = input.readLine();
                        myDate = Arithmetics.addMilli(myDate, Integer.parseInt(milliseconds));
                        printDateFormat(myDate, input);
                        break;
                    case "0":
                        flag = false;
                        break;
                    default:
                        System.out.println("Неверный ввод!");
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void difference() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String formatType = null;
        try {
            do {
                System.out.println("Выберите формат:");
                System.out.println("1. dd/mm/yy - 00:00:00:000");
                System.out.println("2. m/d/yyyy - 00:00:00:000");
                System.out.println("3. mmm-d-yy - 00:00:00:000");
                System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
                formatType = reader.readLine();
            } while (Integer.parseInt(formatType) > 4 || Integer.parseInt(formatType) <= 0);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Введите первую дату:");
        String firstDate = null;

        boolean isValid = true;
        try {
            do {
                if (!isValid) {
                    System.out.println("Неверный ввод!");
                }
                firstDate = reader.readLine();
                isValid = false;
            } while (CheckFormat.check(firstDate, formatType) || CheckFormat.checkFormatOfTime(firstDate));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        DateClass myFirstDate = null;
        try {
            myFirstDate = convert.stringToDate(firstDate, formatType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Введите вторую дату:");
        String secondDate;

        boolean isValidValue = true;
        DateClass mySecondDate = null;
        try {
            do {
                if (!isValidValue) {
                    System.out.println("Неверный ввод!");
                }
                secondDate = reader.readLine();
                isValidValue = false;
            } while (CheckFormat.check(secondDate, formatType) || CheckFormat.checkFormatOfTime(secondDate));

            mySecondDate = convert.stringToDate(secondDate, formatType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long result = Arithmetics.difference(myFirstDate, mySecondDate);
        System.out.println("Результат:");
        int years = (int) MillisecondsToDate.millisToYears(result);
        System.out.println("Годы: " + years);
        result -= DateToMilliseconds.yearToMillis(years);
        int days = (int) MillisecondsToDate.millisToDays(result);
        System.out.println("Дни: " + days);
        result -= DateToMilliseconds.daysToMillis(days);
        int hours = (int) MillisecondsToDate.millisToHours(result);
        System.out.println("Часы:" + hours);
        result -= DateToMilliseconds.hoursToMillis(hours);
        int minutes = (int) MillisecondsToDate.millisToMinutes(result);
        System.out.println("Минуты:" + minutes);
        result -= DateToMilliseconds.minutesToMillis(minutes);
        int seconds = (int) MillisecondsToDate.millisToSeconds(result);
        System.out.println("Секунды:" + seconds);
        result -= DateToMilliseconds.secondsToMillis(seconds);
        System.out.println("Миллисекунды:" + (int) result % 1000);
        System.out.println();
    }

    private static void operations() {
        System.out.println();
        System.out.println("1 - добавление");
        System.out.println("2 - вычитание");
        System.out.println("3 - разница");
        System.out.println("0 - назад");
    }

    private static void inputType() {
        System.out.println();
        System.out.println("1 - Года");
        System.out.println("2 - Дни");
        System.out.println("3 - Часы");
        System.out.println("4 - Минуты");
        System.out.println("5 - Секунды");
        System.out.println("6 - Милисекунды");
        System.out.println("0 - Выход");
        System.out.println();
    }

    private static void printDateFormat(DateClass date, BufferedReader input) {
        try {
            System.out.println("Выберите формат:");
            System.out.println("1. dd/mm/yy - 00:00:00:000");
            System.out.println("2. m/d/yyyy - 00:00:00:000");
            System.out.println("3. mmm-d-yy - 00:00:00:000");
            System.out.println("4. dd-mmm-yyyy - 00:00:00:000");
            String format = input.readLine();
            System.out.println("Дата:");
            System.out.print(datePrint.dateToString(date, format));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
