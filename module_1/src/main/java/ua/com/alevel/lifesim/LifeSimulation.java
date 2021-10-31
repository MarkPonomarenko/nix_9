package ua.com.alevel.lifesim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class LifeSimulation {
    public static void printBoard(int[][] board) {
        System.out.print("  ");
        for(int i = 0; i < board[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for(int i = 0; i < board.length; i++) {
            System.out.print(i + ". ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------------------------");
    }
    public static void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = getNeighbors(board, m, n, i, j);

                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3;
                }
                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;
            }
        }
    }

    public static int getNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0;
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                lives += board[x][y] & 1;
            }
        }
        lives -= board[i][j] & 1;
        return lives;
    }
    public static boolean safeChoice(){
        Scanner input = new Scanner(System.in);
        String choice;
        while(true){
            choice = input.nextLine();
            if (!Objects.equals(choice, "")) {
                if (Integer.parseInt(choice) == 1) return true;
                else if (Integer.parseInt(choice) == 0) return false;
            }
            System.out.println("Неверные данные, введите ещё раз");
        }
    }
    public static int[] safeInput() throws IOException{
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String sizeRaw = input.readLine();
            String[] sizeSplit = sizeRaw.split(" ");
            int[] sizeFin = new int[sizeSplit.length];
            if (sizeSplit.length < 3 && sizeSplit.length != 1) {
                sizeFin[0] = Integer.parseInt(sizeSplit[0]);
                sizeFin[1] = Integer.parseInt(sizeSplit[1]);
                if (sizeFin[0] > 0 && sizeFin[1] > 0 ) {
                    return sizeFin;
                }
            }
        }
    }
    public static void run() throws IOException {
        System.out.println("\tИгра <Жизнь>");
        System.out.println("Начальная доска:");
        System.out.println("Введите размерность (например 3 3)");
        int[] size = safeInput();
        int[][] board = new int[size[0]][size[1]];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                board[i][j] = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            }
        }
        printBoard(board);
        System.out.println("Продолжить/закончить (1/0)");
        if (!safeChoice()) {
            return;
        }
        int x = 0;
        while (true) {
            gameOfLife(board);
            printBoard(board);
            System.out.println("Продолжить/закончить (1/0)");
            if (!safeChoice()) {
                return;
            }
        }
    }
}
