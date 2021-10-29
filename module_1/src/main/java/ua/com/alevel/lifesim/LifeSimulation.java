package ua.com.alevel.lifesim;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class LifeSimulation {
    public static void printBoard(int[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8");
        int j = 0;
        for(int i = 0; i < board.length; i++) {
            System.out.println((i + 1) + " " + board[i][j++] + " " + board[i][j++] + " " +
                    board[i][j++] + " " + board[i][j++] + " " + board[i][j++]+ " "
                    + board[i][j++]+ " " + board[i][j++]+ " " + board[i][j++]);
            j = 0;
        }
        System.out.println("----------------------------------------------");
    }
    public static void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);

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

    public static int liveNeighbors(int[][] board, int m, int n, int i, int j) {
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
    public static void run() throws IOException {
        System.out.println("\tИгра <Жизнь>");
        System.out.println("Начальная доска:");
        int[][] board = {{0,0,0,0,0,0,0,0},
                         {1,1,1,1,1,0,0,0},
                         {1,1,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0}};
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
