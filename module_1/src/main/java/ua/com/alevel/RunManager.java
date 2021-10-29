package ua.com.alevel;

import ua.com.alevel.firstlevel.*;
import ua.com.alevel.lifesim.LifeSimulation;
import ua.com.alevel.secondlevel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunManager {

    public static void run(){
        printMenu();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String statement;
        try {
            while ((statement = input.readLine()) != null) {
                switch (statement) {
                    case "1":
                        printFirstLevel(input);
                        break;
                    case "2":
                        printSecondLevel(input);
                        break;
                    case "3":
                        LifeSimulation.run();
                        printMenu();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неверные данные. Введите еще раз.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printSecondMenu(){
        System.out.println("\tПервый уровень");
        System.out.println("1 - Балансированые скобки");
        System.out.println("2 - Глубина бинарного дерева");
        System.out.println("0 - Выход в главное меню");
    }
    public static void printFirstMenu(){
        System.out.println("\tПервый уровень");
        System.out.println("1 - Кол-во уникальных чисел");
        System.out.println("2 - Ход коня");
        System.out.println("3 - Площадь треугольника");
        System.out.println("0 - Выход в главное меню");
    }
    public static void printFirstLevel(BufferedReader input) throws IOException {
        printFirstMenu();
        String statement;
        try {
            while ((statement = input.readLine()) != null) {
                switch (statement) {
                    case "1":
                        UniqueCounter.run();
                        printFirstMenu();
                        break;
                    case "2":
                        HorseMove.run();
                        printFirstMenu();
                        break;
                    case "3":
                        TriangleArea.run();
                        printFirstMenu();
                        break;
                    case "0":
                        printMenu();
                        return;
                    default:
                        System.out.println("Неверные данные. Введите еще раз.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printSecondLevel(BufferedReader input) throws IOException {
        printSecondMenu();
        String statement;
        try {
            while ((statement = input.readLine()) != null) {
                switch (statement) {
                    case "1":
                        BalancedBrackets.run();
                        printSecondMenu();
                        break;
                    case "2":
                        BinaryTree.findMaxHeight();
                        printSecondMenu();
                        break;
                    case "0":
                        printMenu();
                        return;
                    default:
                        System.out.println("Неверные данные. Введите еще раз.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printMenu() {
        System.out.println("\tГлавное меню");
        System.out.println("Выберите уровень сложности(1,2)");
        System.out.println("3 - Игра Жизнь");
        System.out.println("Введите 0 для выхода");
        System.out.println("Ваш выбор:");
    }
}