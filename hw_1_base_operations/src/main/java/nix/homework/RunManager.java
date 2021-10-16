package nix.homework;

import nix.homework.functional.FirstTask;
import nix.homework.functional.SecondTask;
import nix.homework.functional.ThirdTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunManager{

    public static void run(){
        instruction();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String statement;
        try {
            while((statement = input.readLine()) != null){
                switch(statement){
                    case "1": {
                        new FirstTask().run(input);
                        break;
                    }
                    case "2": {
                        new SecondTask().run(input);
                        break;
                    }
                    case "3": {
                        new ThirdTask().run(input);
                        break;
                    }
                    case "0": {
                        System.exit(0);
                    } break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void instruction(){
        System.out.println("\t Главное меню");
        System.out.println("Введите номер задания для его выполнения.");
        System.out.println("Введите 0 для выхода из программы.");
    }
}