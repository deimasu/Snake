package ru.xc0re.games.snake.model;

import ru.xc0re.games.snake.enums.Direction;
import ru.xc0re.games.snake.enums.GameUnit;

import java.util.ArrayList;

public class Snake {

    private ArrayList<SnakePart> snakeBody = new ArrayList<SnakePart>();

    private Direction direction;

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

        this.direction = Direction.UP;

    }

    public SnakePart getLastPartLastSeen() {
        return lastPartLastSeen;
    }

    public int getLength() {
        return snakeBody.size();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean move() {

        lastPartLastSeen.setX(snakeBody.get(snakeBody.size() - 1).getX());
        lastPartLastSeen.setY(snakeBody.get(snakeBody.size() - 1).getY());

        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).setX(snakeBody.get(i - 1).getX());
            snakeBody.get(i).setY(snakeBody.get(i - 1).getY());
        }

        if (direction == Direction.UP) {
            if (snakeBody.get(0).getY() != 0)
                snakeBody.get(0).setY(snakeBody.get(0).getY() - 1);
            else
                snakeBody.get(0).setY(Board.HEIGHT - 1);
        }
        else if (direction == Direction.RIGHT) {
            if (snakeBody.get(0).getX() != Board.WIDTH - 1)
                snakeBody.get(0).setX(snakeBody.get(0).getX() + 1);
            else
                snakeBody.get(0).setX(0);
        }
        else if (direction == Direction.DOWN) {
            if (snakeBody.get(0).getY() != Board.HEIGHT - 1)
                snakeBody.get(0).setY(snakeBody.get(0).getY() + 1);
            else
                snakeBody.get(0).setY(0);
        }
        else if (direction == Direction.LEFT) {
            if (snakeBody.get(0).getX() != 0)
                snakeBody.get(0).setX(snakeBody.get(0).getX() - 1);
            else
                snakeBody.get(0).setX(Board.WIDTH - 1);
        }

        if (Board.getInstance().isFieldEmpty(snakeBody.get(0).getX(), snakeBody.get(0).getY())) {
            Board.getInstance().set(lastPartLastSeen.getX(), lastPartLastSeen.getY(), this);
            Board.getInstance().set(snakeBody.get(0).getX(), snakeBody.get(0).getY(), snakeBody.get(0));
            return true;
        }
        if (Board.get(snakeBody.get(0).getX(), snakeBody.get(0).getY()) == GameUnit.FOOD) {
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

    public Direction getDirection() {
        return direction;
    }
}
