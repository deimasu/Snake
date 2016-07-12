package ru.xc0re.games.snake.model;

public class SnakePart {

    private int x;
    private int y;

    public SnakePart(int x, int y) {

        this.x = x;
        this.y = y;

        Board.getInstance().set(x, y, this);

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
