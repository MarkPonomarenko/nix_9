package nix.homework.functional;

import nix.homework.RunManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThirdTask{

    public static boolean secureInput(String in){
        char[] arr = in.toCharArray();
        for (char c : arr) {
            if(!Character.isDigit(c)) return false;
        }
        int x = Integer.parseInt(in);
        if (x < 1 || x > 10) return false;
        return true;
    }

    public static void run(BufferedReader input) throws IOException{
        System.out.println("Выбрано третье задание!");
        System.out.println("Введите номер урока (1-10):");
        int lessonTime = 45, oddBreak = 5, evenBreak = 15;
        String rawInput = input.readLine();
        while (secureInput(rawInput) == false) {
            System.out.println("Введите корректные данные");
            input = new BufferedReader(new InputStreamReader(System.in));
            rawInput = input.readLine();
        }
        int x = Integer.parseInt(rawInput);
        x = x*lessonTime + (x/2)*oddBreak + ((x+1)/2 - 1)*evenBreak;
        int hour = x/60 + 9;
        int minute = x%60;
        System.out.println("Время окончания уроков: " + hour + " " + minute);
        System.out.println("Нажмите Enter для продолжения...");
        System.in.read();
        new RunManager().run();
    }
}