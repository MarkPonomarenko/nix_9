package nix.homework.functional;

import nix.homework.RunManager;

import java.io.BufferedReader;
import java.io.IOException;

public class FirstTask{

    public static void run(BufferedReader input) throws IOException{
        System.out.println("Выбрано первое задание");
        System.out.println("Введите строку:");
        String text = input.readLine();
        int sum = 0;
        String temp = "0";
        char[] arr = text.toCharArray();
        for(int i = 0; i < arr.length; ++i) {
            if(Character.isDigit(arr[i]) == true) {
                temp += arr[i];
            }
            else {
                sum += Integer.parseInt(temp);
                temp = "0";
            }
        }
        sum += Integer.parseInt(temp);
        System.out.println("Сумма чисел = " + sum);
        System.out.println("Нажмите Enter для продолжения...");
        System.in.read();
        new RunManager().run();
    }
}