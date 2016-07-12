package ru.xc0re.games.snake;

import java.util.Random;

public class Food {

    private int x;
    private int y;

    public Food() {

        Random random = new Random();

        int x;
        int y;

        do {
            x = random.nextInt(Board.WIDTH);
            y = random.nextInt(Board.HEIGHT);
        }
        while (!Board.getInstance().isFieldEmpty(x, y));

        Board.getInstance().set(x, y, this);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
