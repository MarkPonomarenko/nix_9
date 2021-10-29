package ua.com.alevel.firstlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;


public class UniqueCounter {
    public static boolean inputCheck(String[] input){
        for (String s : input) {
            for (int j = 0; j < s.length(); ++j) {
                if (!Character.isDigit(s.charAt(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void run() throws IOException {
        System.out.println("Введите числа черз пробел:");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String[] numbers;
        while (true) {
            String text = input.readLine();
            numbers = text.split(" ");
            if(inputCheck(numbers)) break;
            else System.out.println("Неверный ввод, попробуйте ещё");
        }
        int[] items = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
            items[i] = Integer.parseInt(numbers[i]);
        }
        int result = 0;
        for (int i = 0; i < numbers.length; i++) {
            int j;
            for (j = 0; j < i; j++){
                if(items[i] == items[j])
                    break;
            }
            if(i == j) {
                result++;
            }
        }
        System.out.println("Кол-во уникальных числе:");
        System.out.println(result);
        System.out.println("Нажмите Enter для продолжения...");
        new Scanner(System.in).nextLine();
    }
}