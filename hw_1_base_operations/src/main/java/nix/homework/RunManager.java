package nix.homework;

import nix.homework.functional.FirstTask;
import nix.homework.functional.SecondTask;

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
                    }
                    case "2": {
                        new SecondTask().run();
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
        System.out.println("");
    }
}