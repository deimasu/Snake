package ru.xc0re.games.snake;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Board {

    /*
    * 0 - empty field
    * 1 - wall
    * 2 - snake's body part
    * 3 - a piece of food
     */

    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;

    private static int[][] boardBody;

    private static Board instance;
    public static boolean foodExists;

    private Board() throws IOException {

        boardBody = new int[HEIGHT][WIDTH];

        FileInputStream fin = new FileInputStream(new File("src/main/resources/level1.map"));

        byte[] code = new byte[fin.available()];

        fin.read(code);
        fin.close();

        int counter = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (code[counter] == 49)
                    boardBody[i][j] = 1;
                else boardBody[i][j] = 0;
                counter++;
            }
        }
    }

    public static Board getInstance() {
        if (instance == null) {
            try {
                instance = new Board();
            } catch (IOException e) {
            }
        }
        return instance;
    }

    public boolean isFieldEmpty(int x, int y) {
        return boardBody[y][x] == 0;
    }

    public void set(int x, int y, Object o) {

        if (o instanceof Snake) {
            boardBody[y][x] = 0;
        } else if (o instanceof Food) {
            boardBody[y][x] = 3;
        } else if (o instanceof SnakePart) {
            boardBody[y][x] = 2;
        }

    }

    public static void printBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                System.out.print(boardBody[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int get(int x, int y) {
        return boardBody[y][x];
    }

}

