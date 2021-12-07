package ua.com.alevel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunController {

    public static void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        try {
            printMenu();
            while ((choice = input.readLine()) != null) {
                options(choice);
                choice = input.readLine();
                if (choice.equals("0")) {
                    System.exit(0);
                }
                options(choice);
            }
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("\tГлавное меню");
        System.out.println("1 - Рассчитать даты");
        System.out.println("2 - Сортировка дат");
        System.out.println("0 - Выход");
        System.out.println("Выбор:");
    }

    public static void options(String choice) {
        switch (choice) {
            case "1":
                ArithmeticsController.run();
                break;
            case "2":
                CreatingController.run();
                break;
            case "0":
                System.exit(0);
            default:
                System.out.println("Неверный ввод!");
        }
        printMenu();
    }
}
