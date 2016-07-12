package ru.xc0re.games.snake;

import java.util.ArrayList;

public class Snake {

    private ArrayList<SnakePart> snakeBody = new ArrayList<SnakePart>();

    /*
    * 0 - up
    * 1 - right
    * 2 - down
    * 3 - left
     */

    private int direction;

    // a snake part that reserves place to added part when food eaten

    private SnakePart lastPartLastSeen;

    // creates a 5 units length snake in the middle of the window

    public Snake() {

        snakeBody.add(new SnakePart(Board.WIDTH / 2, Board.HEIGHT / 2 - 2));
        snakeBody.add(new SnakePart(snakeBody.get(0).getX(), snakeBody.get(0).getY() + 1));
        snakeBody.add(new SnakePart(snakeBody.get(0).getX(), snakeBody.get(1).getY() + 1));
        snakeBody.add(new SnakePart(snakeBody.get(0).getX(), snakeBody.get(2).getY() + 1));
        snakeBody.add(new SnakePart(snakeBody.get(0).getX(), snakeBody.get(3).getY() + 1));
        lastPartLastSeen = new SnakePart(snakeBody.get(0).getX(), snakeBody.get(4).getX() + 1);

        this.direction = 0;

    }

    public SnakePart getLastPartLastSeen() {
        return lastPartLastSeen;
    }

    public int getLength() {
        return snakeBody.size();
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean move() {

        lastPartLastSeen.setX(snakeBody.get(snakeBody.size() - 1).getX());
        lastPartLastSeen.setY(snakeBody.get(snakeBody.size() - 1).getY());

        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        if (direction == 0)
            snakeBody.get(0).setY(snakeBody.get(0).getY() - 1);
        else if (direction == 1)
            snakeBody.get(0).setX(snakeBody.get(0).getX() + 1);
        else if (direction == 2)
            snakeBody.get(0).setY(snakeBody.get(0).getY() + 1);
        else if (direction == 3)
            snakeBody.get(0).setX(snakeBody.get(0).getX() - 1);

        if (Board.getInstance().isFieldEmpty(snakeBody.get(0).getX(), snakeBody.get(0).getY())) {
            Board.getInstance().set(lastPartLastSeen.getX(), lastPartLastSeen.getY(), this);
            Board.getInstance().set(snakeBody.get(0).getX(), snakeBody.get(0).getY(), snakeBody.get(0));
            return true;
        }
        if (Board.get(snakeBody.get(0).getX(), snakeBody.get(0).getY()) == 3) {
            grow();
            Board.foodExists = false;
            return true;
        }

        return false;

    }

    public void grow() {
        snakeBody.add(new SnakePart(lastPartLastSeen.getX(), lastPartLastSeen.getY()));
    }

    public SnakePart get(int index) {
        return snakeBody.get(index);
    }

    public int getDirection() {
        return direction;
    }
}
