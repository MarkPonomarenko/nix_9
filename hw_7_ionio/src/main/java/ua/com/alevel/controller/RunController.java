package ua.com.alevel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RunController {

    public void run() {
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
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printMenu() {
        System.out.println("\tОпции");
        System.out.println("1 - работа с курсами");
        System.out.println("2 - работа с студентами");
        System.out.println("0 - выход");
        System.out.println("Ваш выбор:");
    }

    private void options(String choice) {
        switch (choice) {
            case "1" -> new CourseController().run();
            case "2" -> new StudentController().run();
            case "0" -> System.exit(0);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

}