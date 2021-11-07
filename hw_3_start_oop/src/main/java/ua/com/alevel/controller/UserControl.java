package ua.com.alevel.controller;

import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class UserControl {

    private final UserService userService = new UserService();

    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        try {
            printMenu();
            while ((choice = input.readLine()) != null) {
                options(choice, input);
                choice = input.readLine();
                if (choice.equals("0")) {
                    System.exit(0);
                }
                options(choice, input);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printMenu() {
        System.out.println("\tОпции");
        System.out.println("1 - создать пользователя");
        System.out.println("2 - обновить пользователя");
        System.out.println("3 - удалить пользователя");
        System.out.println("4 - найти по идентификатору");
        System.out.println("5 - найти всех");
        System.out.println("0 - выход");
        System.out.println("Ваш выбор:");
    }

    private void options(String choice, BufferedReader input) {
        switch (choice) {
            case "1":
                create(input);
                break;
            case "2":
                update(input);
                break;
            case "3":
                delete(input);
                break;
            case "4":
                findById(input);
                break;
            case "5":
                findAll(input);
                break;
            case "0":
                System.exit(0);
            default:
                System.out.println("Нет такой опции");
        }
        printMenu();
    }

    private int safeInput(String rawAge) {
        for (int i = 0; i < rawAge.length(); ++i) {
            if (!Character.isDigit(rawAge.charAt(i))) {
                return -1;
            }
        }
        return Integer.parseInt(rawAge);
    }

    private void create(BufferedReader input) {
        System.out.println("Создание пользователя:");
        try {
            System.out.println("Введите имя:");
            String inName = input.readLine();
            System.out.println("Введите возраст:");
            int age;
            String ageRaw;
            while (true) {
                ageRaw = input.readLine();
                if ((age = safeInput(ageRaw)) != -1) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            User user = new User();
            user.setAge(age);
            user.setName(inName);
            userService.create(user);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void update(BufferedReader input) {
        System.out.println("Обновление пользователя");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            System.out.println("Введите имя:");
            String name = input.readLine();
            System.out.println("Введите возраст:");
            int age;
            String ageRaw;
            while (true) {
                ageRaw = input.readLine();
                if ((age = safeInput(ageRaw)) != -1) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            userService.update(user);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void delete(BufferedReader input) {
        System.out.println("Удаление пользователя:");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            userService.delete(id);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void findById(BufferedReader input) {
        System.out.println("Поиск по идентификатору:");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            User user = userService.findById(id);
            if (user == null)
                System.out.println("Нет такого");
            else
                System.out.println("Позьзователь: " + user);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void findAll(BufferedReader input) {
        System.out.println("Поиск всех");
        User[] users = userService.findAll();
        if (users != null && users.length != 0) {
            for (User user : users) {
                System.out.println("Пользователь: " + user);
            }
        } else {
            System.out.println("No users");
        }
    }
}