package managerClasses;

import string.methods.StringReverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IndexStringReverse {

    public static void call() throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строку:");
        String text = input.readLine();
        int firstIndex = 0;
        int lastIndex = 0;
        try {
            while (true) {
                System.out.println("Введите первый индекс:");
                firstIndex = Integer.parseInt(input.readLine());
                System.out.println("Введите последний индекс:");
                lastIndex = Integer.parseInt(input.readLine());
                if (firstIndex >= lastIndex || lastIndex < 0 || lastIndex > text.length()) {
                    System.out.println("Неверные индексы. Введите ещё раз");
                }
                else {
                    text = StringReverse.reverse(text, firstIndex, lastIndex);
                    System.out.println(text);
                    break;
                }
            }
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