package ua.com.alevel.controller;

import ua.com.alevel.taskfirst.DateFileReader;
import ua.com.alevel.tasksecond.UniqueNameFinder;
import ua.com.alevel.taskthird.ShortestPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunController {

    public static void run() {
        printMenu();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String statement;
        try {
            while ((statement = input.readLine()) != null) {
                switch (statement) {
                    case "1":
                        new DateFileReader().run();
                        break;
                    case "2":
                        new UniqueNameFinder().run();
                        break;
                    case "3":
                        new ShortestPath().run();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неверные данные. Введите еще раз.");
                }
                printMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void printMenu() {
        System.out.println("1 - обработка дат");
        System.out.println("2 - поиск уникального имени");
        System.out.println("3 - поиск кратчайшего путя");
        System.out.println("0 - выход");
    }
}
