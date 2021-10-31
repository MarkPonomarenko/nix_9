package ua.com.alevel.firstlevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HorseMove {

    public static void printBoard(int[][] board) {
        System.out.print("  ");
        for (int i = 0; i < board.length; i++) {
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
    }
    public static void printBoard(int[][] board, int x, int y) {
        System.out.print("  ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(" " + i);
        }
        int tmp = board[x][y];
        board[x][y] = 1;
        System.out.print("\n");
        for(int i = 0; i < board.length; i++) {
            System.out.print(i + ". ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        board[x][y] = tmp;
    }
    public static int[] safeInput() throws IOException{
        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String position = input.readLine();
            String[] posSplit = position.split(" ");
            int[] posFin = new int[posSplit.length];
            if (posSplit.length < 3 && posSplit.length != 1) {
                posFin[0] = Integer.parseInt(posSplit[0]);
                posFin[1] = Integer.parseInt(posSplit[1]);
                if (posFin[0] > 0 && posFin[0] < 9 && posFin[1] > 0 && posFin[1] < 9) {
                    return posFin;
                }
            }
            if (posSplit.length == 1) {
                if (posFin[0] == 0)
                    return posFin;
            }
        }
    }
    public static boolean validMove(int[] start, int[] destination) {
        int deltaFirst = destination[0] - start[0];
        int deltaSecond = destination[1] - start[1];
        return 5 == deltaFirst*deltaFirst + deltaSecond*deltaSecond;
    }
    public static void run() throws IOException {
        int[][] board = {{0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0}};
        printBoard(board);
        System.out.println("Введите начальную позицию:");
        int[] pos = safeInput();
        printBoard(board, pos[0], pos[1]);
        while (true) {
            System.out.println("Введите координаты направления (для выхода - 0):");
            int[] move = safeInput();
            if (move[0] == 0) break;
            if (validMove(pos, move)) {
                pos[0] = move[0];
                pos[1] = move[1];
                printBoard(board, pos[0], pos[1]);
            } else {
                System.out.println("Ход невозможен!");
            }
        }
    }
}