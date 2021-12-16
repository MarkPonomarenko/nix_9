package ua.com.alevel.parser;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CSVParser {

    public CSVParser() {

    }

    public static final String SEPARATOR = ";";

    //потоки для записи файла в виде двумерного списка
    public static List<List<String>> read(String fileName) {
        String correctPath = getPath(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(correctPath))) {
            return reader.lines()
                    .skip(1)                                    //пропускаем первую строку (название столбцов)
                    .filter(Objects::nonNull)                   //проверяем на наличие строки
                    .map(string -> string.split(SEPARATOR))
                    .map(strings -> Arrays.stream(strings).collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean write(List<List<String>> ent, String file) throws IOException {
        String correctPath = getPath(file);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(correctPath, true)))) {
            ent.stream()
                    .map(CSVParser::entityToCsv)
                    .forEach(writer::println);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String avoidSpecials(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private static String entityToCsv(List<String> entityList) {
        return entityList.stream()
                .map(CSVParser::avoidSpecials)
                .collect(Collectors.joining(","));
    }

    public static boolean isEmpty(String file) {
        String correctPath = getPath(file);
        try (BufferedReader br = new BufferedReader(new FileReader(correctPath))) {
            return br.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    //получаем путь к csv файлу от модуля парсера
    public static String getPath(String file) {
        String path = CSVParser.class.getClassLoader().getResource(file).getPath();
        String substring = path.substring(0, path.lastIndexOf("target")).replace("file:/", "");
        return substring.concat("src/main/resources/").concat(file);
    }
}
