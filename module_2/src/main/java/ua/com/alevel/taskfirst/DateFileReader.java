package ua.com.alevel.taskfirst;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateFileReader {

    static final String input_file = "src/main/resources/dates/datesIn.txt";
    static final String output_file = "src/main/resources/dates/datesOut.txt";

    public void run() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(input_file));
        String current;
        List<String> formats = Arrays.asList("y/M/d", "d/M/y", "M-d-y");
        DateTimeFormatter formatter;
        String buffer;
        List<String> result = new ArrayList<>();
        LocalDate date;

        while ((current = fileReader.readLine()) != null) {
            String[] dates = current.split(" ");
            for (String dateIt : dates) {
                buffer = "";
                for (String format : formats) {
                    formatter = DateTimeFormatter.ofPattern(format);
                    try {
                        date = LocalDate.parse(dateIt, formatter);
                        buffer += date.getYear();
                        if (date.getMonthValue() < 10)
                            buffer += "0" + date.getMonthValue();
                        else
                            buffer += date.getMonthValue();
                        if (date.getDayOfMonth() < 10)
                            buffer += "0" + date.getDayOfMonth();
                        else
                            buffer += date.getDayOfMonth();
                        result.add(buffer);
                    } catch (DateTimeParseException e) {
                    }
                }
            }
        }
        fileReader.close();

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(output_file));
        for (String out : result) {
            System.out.println(out);
            fileWriter.write(out + "\n");
        }
        System.out.println("Результат так же был записан в: " + output_file);
        fileWriter.close();
    }
}