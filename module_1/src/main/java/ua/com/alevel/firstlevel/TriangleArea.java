package ua.com.alevel.firstlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TriangleArea {

    public static boolean triangleExist(int[] a, int[] b, int[] c) {
        double ab = Math.sqrt(Math.pow((b[0] - a[0]), 2) + Math.pow((b[1] - a[1]), 2));
        double ac = Math.sqrt(Math.pow((c[0] - a[0]), 2) + Math.pow((c[1] - a[1]), 2));
        double bc = Math.sqrt(Math.pow((c[0] - b[0]), 2) + Math.pow((c[1] - b[1]), 2));
        return ab + ac > bc && ab + bc > ac && ac + bc > ab;
    }

    public static int[] pointInput() throws IOException {
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String position = input.readLine();
            String[] posSplit = position.split(" ");
            int[] pointCoord = new int[posSplit.length];
            if (posSplit.length < 3 && posSplit.length != 1) {
                pointCoord[0] = Integer.parseInt(posSplit[0]);
                pointCoord[1] = Integer.parseInt(posSplit[1]);
                return pointCoord;
            }
            if (posSplit.length == 1) {
                if (pointCoord[0] == 0)
                    return pointCoord;
            }
            System.out.println("Неверный ввод, попробуйте ещё");
        }
    }

    public static double calculateArea(int[] a, int[] b, int[] c) {
        return Math.abs((a[0] * (b[1] - c[1]) + b[0] * (c[1] - a[1]) + c[0] * (a[1] - b[1])) / 2);
    }

    public static void run() throws IOException {
        System.out.println("Введите координаты первой точки:");
        int[] firstPoint = pointInput();
        System.out.println("Введите координаты второй точки:");
        int[] secondPoint = pointInput();
        System.out.println("Введите координаты третьей точки:");
        int[] thirdInput = pointInput();

        if (triangleExist(firstPoint, secondPoint, thirdInput)) {
            System.out.println("Площадь треугольника:");
            System.out.println(calculateArea(firstPoint, secondPoint, thirdInput));
        } else {
            System.out.println("Такой треугольник невозможен");
        }
        System.out.println("Нажмите Enter для продолжения...");
        new Scanner(System.in).nextLine();
    }
}