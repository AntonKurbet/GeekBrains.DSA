package ru.geekbrains.dsa.lesson5;

public class Main {
    private static int moves[][] = {{1,0},{0,1}};
    private static int desk[][] = new int[5][5];
    private static int[] queens = new int[8];

    public static void king(int x, int y, int toX, int toY) {
        if (x == toX && y == toY) {
            //desk[x][y]++;
            return;
        }
        for (int i = 0; i < moves.length; i++) {
            int nextX = x + moves[i][0];
            int nextY = y + moves[i][1];
            if (nextX > toX || nextY > toY) continue;
            desk[nextX][nextY]++;
            king(nextX,nextY,toY,toX);
        }

    }

    public static void queen(int n) {

    }

    public static void main(String[] args) {
        king(0,0, desk.length - 1, desk[0].length - 1);
        //queen(7);
        printDesk();
    }

    private static void printDesk() {
        for (int x = 0; x < desk.length; x++) {
            for (int y = 0; y < desk[0].length; y++) {
                System.out.printf("%5d",desk[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
