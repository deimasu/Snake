package ru.xc0re.games.snake.model;

import ru.xc0re.games.snake.enums.GameUnit;

import java.io.IOException;
import java.io.InputStream;

public class Board {

    /*
    * 0 - empty field
    * 1 - wall
    * 2 - snake's body part
    * 3 - a piece of food
     */

    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;

    private static GameUnit[][] boardBody;

    private static Board instance;
    public static boolean foodExists;

    private Board()  {

        try {
            boardBody = new GameUnit[HEIGHT][WIDTH];

            InputStream fin = Board.class.getClassLoader().getResourceAsStream("level3.map");

            byte[] code = new byte[fin.available()];

            fin.read(code);
            fin.close();

            int counter = 0;

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (code[counter] == 49)
                        boardBody[i][j] = GameUnit.WALL;
                    else boardBody[i][j] = GameUnit.EMPTY_SPACE;
                    counter++;
                }
            }
        }
        catch (IOException e) {}

    }

    public static Board getInstance() {
        if (instance == null) {

            instance = new Board();

        }
        return instance;
    }

    public boolean isFieldEmpty(int x, int y) {
        return boardBody[y][x] == GameUnit.EMPTY_SPACE;
    }

    public void set(int x, int y, Object o) {

        if (o instanceof Snake) {
            boardBody[y][x] = GameUnit.EMPTY_SPACE;
        } else if (o instanceof Food) {
            boardBody[y][x] = GameUnit.FOOD;
        } else if (o instanceof SnakePart) {
            boardBody[y][x] = GameUnit.SNAKE;
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

    public static GameUnit get(int x, int y) {
        return boardBody[y][x];
    }

}

