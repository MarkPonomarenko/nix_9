package managerClasses;

import string.methods.StringReverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharStringReverse {

    public static void call() throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строку:");
        String text = input.readLine();
        String first;
        String last;
        try {
            System.out.println("Введите первый символ:");
            first = input.readLine();
            System.out.println("Введите последний символ:");
            last = input.readLine();
            char firstChar = first.charAt(0);
            char lastChar = last.charAt(0);
            text = StringReverse.reverse(text, firstChar, lastChar);
            System.out.println(text);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Нажмите Enter для продолжения...");
        System.in.read();
        input.readLine();
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