package ua.com.alevel.tasksecond;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UniqueNameFinder {

    static final String input_file = "src/main/resources/names/namesIn.txt";
    static final String output_file = "src/main/resources/names/namesOut.txt";

    public void write(String result) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(output_file));
        fileWriter.write(result + "\n");
        System.out.println("Результат так же был записан в: " + output_file);
        fileWriter.close();
    }

    public void run() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(input_file));
        String current;
        String result;
        while ((current = fileReader.readLine()) != null) {
            List<String> names = Arrays.asList(current.split(" "));
            for (String name : names) {
                if (names.lastIndexOf(name) == names.indexOf(name)) {
                    result = names.get(names.indexOf(name));
                    System.out.println(result);
                    write(result);
                    fileReader.close();
                    return;
                }
            }
        }
        System.out.println("No unique names!");
        fileReader.close();
    }
}