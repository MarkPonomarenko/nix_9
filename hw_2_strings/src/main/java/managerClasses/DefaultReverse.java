package managerClasses;

import string.methods.StringReverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultReverse {

    public static void call() throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строку:");
        String text = input.readLine();
        System.out.println("1. Реверс всей строки целиком.\n0. Реверс пословно.");
        String choice = "0";
        Boolean exitFlag = false;
        try {
            while ((choice = input.readLine()) != null) {
                switch (choice) {
                    case "1":
                        choice = "true";
                        text = StringReverse.reverse(text, Boolean.parseBoolean(choice));
                        System.out.println(text);
                        exitFlag = true;
                        break;
                    case "0":
                        choice = "false";
                        text = StringReverse.reverse(text, Boolean.parseBoolean(choice));
                        System.out.println(text);
                        exitFlag = true;
                        break;
                    default:
                        System.out.println("Неверные данные введите ещё раз.");
                }
                if (exitFlag) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Нажмите Enter для продолжения...");
        System.in.read(); //при нажатии передается в считыватель выбора
        input.readLine(); //сьедает нажатый ентер что-бы избежать передачи
        printMenu();
    }
    public static void printMenu() {
            System.out.println("\tГлавное меню");
            System.out.println("1. Простой реверс строки");
            System.out.println("2. Реверс подстроки");
            System.out.println("3. Реверс по индексам");
            System.out.println("4. Ревеср между задаными символами");
            System.out.println("5. Реверс между задаными строками");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");
        }
}