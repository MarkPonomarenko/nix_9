package ua.com.alevel.secondlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class BalancedBrackets {
    public static boolean isBalancedLine(String string) {
        Deque<Character> brackets = new ArrayDeque<Character>();
        for (int i = 0; i < string.length(); ++i) {
            char x = string.charAt(i);
            if(x == '(' || x == '[' || x == '{'){
                brackets.push(x);
                continue;
            }
            if (brackets.isEmpty())
                return false;
            char check;
            switch(x) {
                case ')':
                    check = brackets.pop();
                    if(check == '{' || check == '[')
                        return false;
                    break;
                case '}':
                    check = brackets.pop();
                    if(check == '(' || check == '[')
                        return false;
                    break;
                case ']':
                    check = brackets.pop();
                    if (check == '(' || check == '{')
                        return false;
                    break;
            }
        }
        return (brackets.isEmpty());
    }
    public static void run() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = input.readLine();

        if(isBalancedLine(inputLine)){
            System.out.println("Строка допустима");
        }
        else {
            System.out.println("Строка недопустима");
        }
        System.out.println("Нажмите Enter для продолжения...");
        new Scanner(System.in).nextLine();
    }
}