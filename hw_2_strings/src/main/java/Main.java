import managerClasses.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        printMenu();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String statement;
        try {
            while ((statement = input.readLine()) != null){
                switch (statement){
                    case "1":
                        DefaultReverse.call();
                        break;
                    case "2":
                        SubstringReverse.call();
                        break;
                    case "3":
                        IndexStringReverse.call();
                        break;
                    case "4":
                        CharStringReverse.call();
                        break;
                    case "5":
                        ByStringReverse.call();
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