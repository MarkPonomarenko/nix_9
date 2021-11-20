package ua.com.alevel.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLOutput;

import ua.com.alevel.db.MSStorage;
import ua.com.alevel.util.MathSet;

public class RunController {

    private MSStorage mathSets = new MSStorage();

    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        try {
            printMenu();
            while ((choice = input.readLine()) != null) {
                options(choice, input);
                choice = input.readLine();
                if (choice.equals("0")) {
                    System.exit(0);
                }
                options(choice, input);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printMenu() {
        System.out.println("\tОпции");
        System.out.println("1 - создать новый MathSet");
        System.out.println("2 - действия над MathSet");
        System.out.println("0 - выход");
        System.out.println("Ваш выбор:");
    }

    private void options(String choice, BufferedReader input) {
        switch (choice) {
            case "1" -> createHandler(input);
            case "2" -> featuresHandler(input);
            case "0" -> System.exit(0);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

    public void printCreateOptions() {
        System.out.println("\tСоздание MathSet");
        System.out.println("1 - пустой");
        System.out.println("2 - (int capacity)");
        System.out.println("3 - (Number[] numbers)");
        System.out.println("4 - (Number[] ... numbers)");
        System.out.println("5 - (MathSet ms)");
        System.out.println("6 - (MathSet ... ms)");
        System.out.println("0 - Назад");
    }

    public Number safeInput(String rawInput) {
        int count = 0;
        boolean isDouble = false;
        for (int i = 0; i < rawInput.length(); ++i) {
            if (!Character.isDigit(rawInput.charAt(i))) {
                if (rawInput.charAt(i) == '.') {
                    isDouble = !isDouble;
                    count++;
                } else
                    return -1;
            }
        }
        if (!isDouble && count == 0)
            return Integer.parseInt(rawInput);
        if (isDouble && count == 1)
            return Double.parseDouble(rawInput);
        return -1;
    }

    public void createOptions(String choice, BufferedReader input) {
        try {
            switch (choice) {
                case "1": {
                    mathSets.addSet(new MathSet());
                }
                break;
                case "2": {
                    System.out.println("Введите вместимость");
                    String rawInput;
                    int capacity;
                    while (true) {
                        rawInput = input.readLine();
                        if ((capacity = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    MathSet<Double> msTemp = new MathSet<>(capacity);
                    mathSets.addSet(msTemp);
                }
                break;
                case "3": {
                    System.out.println("Введите количество чисел:");
                    String rawInput;
                    int count;
                    while (true) {
                        rawInput = input.readLine();
                        if ((count = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    Number num;
                    Number[] numbers = new Number[count];
                    for (int i = 0; i < count; ++i) {
                        while (true) {
                            rawInput = input.readLine();
                            if ((num = safeInput(rawInput)).intValue() != -1)
                                break;
                            System.out.println("Неверный ввод!");
                        }
                        numbers[i] = num;
                    }
                    mathSets.addSet(new MathSet(numbers));
                }
                break;
                case "4": {
                    System.out.println("\tКол-во массивов чисел:");
                    String rawInput;
                    int count;
                    while (true) {
                        rawInput = input.readLine();
                        if ((count = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    Number[][] numbers = new Number[count][];
                    int inCount;
                    for (int i = 0; i < count; ++i) {
                        System.out.println("Массив №" + i);
                        System.out.println("Введите кол-во чисел:");
                        while (true) {
                            rawInput = input.readLine();
                            if ((inCount = safeInput(rawInput).intValue()) != -1)
                                break;
                            System.out.println("Неверный ввод!");
                        }
                        numbers[i] = new Number[inCount];
                        Number num;
                        for (int j = 0; j < inCount; ++j) {
                            while (true) {
                                rawInput = input.readLine();
                                if ((num = safeInput(rawInput)).intValue() != -1)
                                    break;
                                System.out.println("Неверный ввод!");
                            }
                            numbers[i][j] = num;
                        }
                    }
                    mathSets.addSet(new MathSet(numbers));
                }
                break;
                case "5": {
                    System.out.println("\tВыберите MathSet(введите id)");
                    printAllSets();
                    String id = input.readLine();
                    mathSets.addSet(new MathSet(mathSets.findById(id)));
                }
                break;
                case "6": {
                    System.out.println("\tСколько MathSet'ов хотите использовать?");
                    String rawInput;
                    int count;
                    while (true) {
                        rawInput = input.readLine();
                        if ((count = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    String[] ids = new String[count];
                    System.out.println("Выберите MathSet'ы");
                    printAllSets();
                    String id;
                    MathSet[] sets = new MathSet[count];
                    for (int i = 0; i < count; ++i) {
                        id = input.readLine();
                        ids[i] = id;
                    }
                    int i = 0;
                    for (String idIt : ids) {
                        if (mathSets.findById(idIt) != null)
                            sets[i++] = mathSets.findById(idIt);
                    }
                    mathSets.addSet(new MathSet(sets));
                }
                break;
                case "0": {
                    return;
                }
                default:
                    System.out.println("Неверный ввод!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printAllSets() {
        for (int i = 0; i < mathSets.findAll().length; ++i) {
            System.out.println(mathSets.findAll()[i]);
        }
    }

    public void createHandler(BufferedReader input) {
        String choice;
        try {
            while ((choice = input.readLine()) != null) {
                createOptions(choice, input);
                if (choice.equals("0")) {
                    return;
                }
                printCreateOptions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printFeatureOptions() {
        System.out.println("\tМанипуляции с MathSet");
        System.out.println("1 - вывести все");
        System.out.println("2 - найти по id");
        System.out.println("3 - добавить число");
        System.out.println("4 - добавить числа");
        System.out.println("5 - объединить с другим");
        System.out.println("6 - объединить с другими");
        System.out.println("7 - сортировка по убыванию");
        System.out.println("8 - сортировка по возрастанию");
        System.out.println("9 - получить какой-то элемент");
        System.out.println("10 - пересечение сетов");
        System.out.println("11 - вырезка из сета");
        System.out.println("12 - очистка");
        System.out.println("0 - назад");
    }

    public void featureOptions(String choice, BufferedReader input) {
        try {
            switch (choice) {
                case "1":
                    for (int i = 0; i < mathSets.getSize(); ++i) {
                        System.out.println(mathSets.getAt(i));
                    }
                    break;
                case "2": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();

                    System.out.println(mathSets.findById(id));
                }
                break;
                case "3": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Введите число:");
                    String rawInput;
                    Number num;
                    while (true) {
                        rawInput = input.readLine();
                        if ((num = safeInput(rawInput)).intValue() != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    mathSets.findById(id).add(num);
                }
                break;
                case "4": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Сколько чисел хотите добавить:");
                    String rawInput;
                    int count;
                    while (true) {
                        rawInput = input.readLine();
                        if ((count = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    Number num;
                    Number[] numbers = new Number[count];
                    for (int i = 0; i < count; ++i) {
                        while (true) {
                            rawInput = input.readLine();
                            if ((num = safeInput(rawInput)).intValue() != -1)
                                break;
                            System.out.println("Неверный ввод!");
                        }
                        numbers[i] = num;
                    }
                    mathSets.findById(id).add(numbers);
                }
                break;
                case "5": {
                    System.out.println("Введите идентификатор объединяемого:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Введите идентификатор который хотите присоединить:");
                    String idJoin = input.readLine();
                    if (mathSets.findById(idJoin) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    mathSets.findById(id).join(mathSets.findById(idJoin));
                    System.out.println("Результат:");
                    System.out.println(mathSets.findById(id));
                }
                break;
                case "6": {
                    System.out.println("Введите идентификатор объединяемого:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Сколько MathSet хотити присоединиить?");
                    String rawInput;
                    int count;
                    while (true) {
                        rawInput = input.readLine();
                        if ((count = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    MathSet[] sets = new MathSet[count];
                    for (int i = 0; i < count; ++i) {
                        System.out.println("Введите идентифиактор " + (i + 1));
                        String idIn = input.readLine();
                        sets[i] = mathSets.findById(idIn);
                    }
                    System.out.println("Результат:");
                    mathSets.findById(id).join(sets);
                }
                break;
                case "7": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Выберите способ сортировки:");
                    System.out.println("1 - весь сет\n2 - между двумя индексами\n3 - от числа и до конца");
                    String statement;
                    try {
                        while ((statement = input.readLine()) != null) {
                            switch (statement) {
                                case "1": {
                                    mathSets.findById(id).sortDesc();
                                    System.out.println("Результат:");
                                    System.out.println(mathSets.findById(id));
                                }
                                return;
                                case "2": {
                                    System.out.println("Введите первый идентификатор:");
                                    String rawInput;
                                    int firstIndex;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((firstIndex = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    int secondIndex;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((secondIndex = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    mathSets.findById(id).sortDesc(firstIndex, secondIndex);
                                    System.out.println("Результат:");
                                    System.out.println(mathSets.findById(id));
                                }
                                return;
                                case "3": {
                                    System.out.println("Введите число:");
                                    String rawInput;
                                    Number num;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((num = safeInput(rawInput)).intValue() != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    mathSets.findById(id).sortDesc(num);
                                }
                                return;
                                default:
                                    System.out.println("Неверный ввод!");
                            }
                        }
                    } catch (Exception er) {
                        System.out.println("Error: " + er.getMessage());
                    }
                }
                break;
                case "8": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Выберите способ сортировки:");
                    System.out.println("1 - весь сет\n2 - между двумя индексами\n3 - от числа и до конца");
                    String statement;
                    try {
                        while ((statement = input.readLine()) != null) {
                            switch (statement) {
                                case "1": {
                                    mathSets.findById(id).sortAsc();
                                    System.out.println("Результат:");
                                    System.out.println(mathSets.findById(id));
                                }
                                return;
                                case "2": {
                                    System.out.println("Введите первый индекс:");
                                    String rawInput;
                                    int firstIndex;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((firstIndex = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    System.out.println("Введите второй индекс:");
                                    int secondIndex;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((secondIndex = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    mathSets.findById(id).sortAsc(firstIndex, secondIndex);
                                    System.out.println("Результат:");
                                    System.out.println(mathSets.findById(id));
                                }
                                return;
                                case "3": {
                                    System.out.println("Введите число:");
                                    String rawInput;
                                    Number num;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((num = safeInput(rawInput)).equals(-1))
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    mathSets.findById(id).sortAsc(num);
                                }
                                return;
                                default:
                                    System.out.println("Неверный ввод!");
                            }
                        }
                    } catch (Exception er) {
                        System.out.println("Error: " + er.getMessage());
                    }
                }
                break;
                case "9": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Какой элемент хотите получить?");
                    System.out.println("1 - по индексу\n2 - max\n3 - min\n4 - average\n5 - median");
                    String statement;
                    try {
                        while ((statement = input.readLine()) != null) {
                            switch (statement) {
                                case "1": {
                                    System.out.println("Введите индекс:");
                                    String rawInput;
                                    int index;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((index = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    System.out.println(mathSets.findById(id).get(index));
                                }
                                return;
                                case "2": {
                                    System.out.println("Max = " + mathSets.findById(id).getMax());
                                }
                                return;
                                case "3": {
                                    System.out.println("Min = " + mathSets.findById(id).getMin());
                                }
                                return;
                                case "4": {
                                    System.out.println("Average = " + mathSets.findById(id).getAverage());
                                }
                                return;
                                case "5": {
                                    System.out.println("Median = " + mathSets.findById(id).getMedian());
                                }
                                return;
                                default:
                                    System.out.println("Неверный ввод!");
                            }
                        }
                    } catch (Exception er) {
                        System.out.println("Error: " + er.getMessage());
                    }
                }
                break;
                case "10": {
                    System.out.println("Пересечение");
                    System.out.println("1 - между двумя сетами\n2 - более чем двумя");
                    String statement;
                    try {
                        while ((statement = input.readLine()) != null) {
                            switch (statement) {
                                case "1": {
                                    System.out.println("Введите идентифкатор первого: ");
                                    String idFirst = input.readLine();
                                    if (mathSets.findById(idFirst) == null) {
                                        System.out.println("MathSet не найден");
                                        return;
                                    }
                                    System.out.println("Введите идентификатор второго: ");
                                    String idSecond = input.readLine();
                                    if (mathSets.findById(idSecond) == null) {
                                        System.out.println("MathSet не найден");
                                        return;
                                    }
                                    System.out.println("Результат:");
                                    mathSets.findById(idFirst).intersection(mathSets.findById(idSecond));
                                    System.out.println(mathSets.findById(idFirst));
                                }
                                printFeatureOptions();
                                return;
                                case "2": {
                                    System.out.println("Введите количество сетов:");
                                    String rawInput;
                                    int count;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((count = safeInput(rawInput).intValue()) != -1 && count > 2)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    MathSet[] sets = new MathSet[count - 1];
                                    String id;
                                    String idFirst = null;
                                    for (int i = 0; i < count; ++i) {
                                        System.out.println("Введите индентификатор №" + (i + 1));
                                        id = input.readLine();
                                        if (mathSets.findById(id) == null) {
                                            System.out.println("MathSet не найден");
                                            return;
                                        }
                                        if (i == 0)
                                            idFirst = id;
                                        else
                                            sets[i - 1] = mathSets.findById(id);
                                    }
                                    System.out.println("Результат:");
                                    mathSets.findById(idFirst).intersection(sets);
                                    System.out.println(mathSets.findById(idFirst));
                                }
                                printFeatureOptions();
                                return;
                                default:
                                    System.out.println("Неверный ввод!");
                            }
                        }
                        System.out.println("Пересечение");
                        System.out.println("1 - между двумя сетами\n2 - более чем двумя");
                    } catch (Exception er) {
                        System.out.println("Error: " + er.getMessage());
                    }
                }
                break;
                case "11": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Введите первый индекс:");
                    String rawInput;
                    int firstIndex;
                    while (true) {
                        rawInput = input.readLine();
                        if ((firstIndex = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    System.out.println("Введите второй индекс:");
                    int secondIndex;
                    while (true) {
                        rawInput = input.readLine();
                        if ((secondIndex = safeInput(rawInput).intValue()) != -1)
                            break;
                        System.out.println("Неверный ввод!");
                    }
                    System.out.println("Результат:");
                    System.out.println(mathSets.findById(id).cut(firstIndex, secondIndex));
                }
                break;
                case "12": {
                    System.out.println("Введите идентификатор:");
                    String id = input.readLine();
                    if (mathSets.findById(id) == null) {
                        System.out.println("MathSet не найден");
                        return;
                    }
                    System.out.println("Способы очистики:");
                    System.out.println("1 - полностью\n2 - удалить заданые числа");
                    String statement;
                    try {
                        while ((statement = input.readLine()) != null) {
                            switch (statement) {
                                case "1": {
                                    mathSets.findById(id).clear();
                                    System.out.println("Результат:");
                                    System.out.println(mathSets.findById(id));
                                }
                                return;
                                case "2": {
                                    System.out.println("Сколько чисел хотите удалить?");
                                    String rawInput;
                                    int count;
                                    while (true) {
                                        rawInput = input.readLine();
                                        if ((count = safeInput(rawInput).intValue()) != -1)
                                            break;
                                        System.out.println("Неверный ввод!");
                                    }
                                    Number num;
                                    Number[] numbers = new Number[count];
                                    for (int i = 0; i < count; ++i) {
                                        while (true) {
                                            rawInput = input.readLine();
                                            if ((num = safeInput(rawInput)).intValue() != -1)
                                                break;
                                            System.out.println("Неверный ввод!");
                                        }
                                        numbers[i] = num;
                                    }
                                    mathSets.findById(id).clear(numbers);
                                    System.out.println("Результат:");
                                    System.out.println((mathSets.findById(id)));
                                }

                                return;
                                default:
                                    System.out.println("Неверный ввод!");
                            }
                        }
                    } catch (Exception er) {
                        System.out.println("Error: " + er.getMessage());
                    }
                }
                break;
                case "0": {
                    return;
                }
                default:
                    System.out.println("Неверный ввод!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void featuresHandler(BufferedReader input) {
        String choice;
        try {
            printFeatureOptions();
            while ((choice = input.readLine()) != null) {
                featureOptions(choice, input);
                if (choice.equals("0")) {
                    return;
                }
                printFeatureOptions();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}