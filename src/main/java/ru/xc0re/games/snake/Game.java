package ru.xc0re.games.snake;

import ru.xc0re.games.snake.enums.Direction;
import ru.xc0re.games.snake.enums.GameUnit;
import ru.xc0re.games.snake.model.Board;
import ru.xc0re.games.snake.model.Food;
import ru.xc0re.games.snake.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements Runnable {

    private Snake snake;
    private Board board;
    private Food food;

    private boolean gameEnded;
    private boolean initialised;

    public static final int ONE_UNIT_SIZE = 15;
    public static final int HEIGHT = Board.HEIGHT * ONE_UNIT_SIZE + 27;
    public static final int WIDTH = Board.WIDTH * ONE_UNIT_SIZE + 5;

    public Game() {

        super("Snake Game");

        snake = new Snake();
        board = Board.getInstance();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    public void run() {

        addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
//                myKeyEvt(e);
            }


            public void keyReleased(KeyEvent e) {
//                myKeyEvt(e);
            }


            public void keyPressed(KeyEvent e) {
                myKeyEvt(e);
            }

            private void myKeyEvt(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
                {
                    if (snake.get(1).getX() != snake.get(0).getX() - 1)
                        snake.setDirection(Direction.LEFT);
                }
                else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
                {
                    if (snake.get(1).getX() != snake.get(0).getX() + 1)
                        snake.setDirection(Direction.RIGHT);
                }
                else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP)
                {
                    if (snake.get(1).getY() != snake.get(0).getY() - 1)
                        snake.setDirection(Direction.UP);
                }
                else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
                {
                    if (snake.get(1).getY() != snake.get(0).getY() + 1)
                        snake.setDirection(Direction.DOWN);
                }
            }
        });

        while (!gameEnded) {

            if (!Board.foodExists) {
                food = new Food();
                Board.foodExists = true;
            }

            if (!snake.move())
                gameEnded = true;

            update(getGraphics());

            setTitle("Snake. Length: " + snake.getLength());

            try {
                repaint();
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



    public void paint(Graphics g) {

        if (!initialised) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Board.WIDTH; i++) {
                for (int j = 0; j < Board.HEIGHT; j++) {
                    if (Board.get(j, i) == GameUnit.WALL) {
                        g.setColor(Color.gray);
                        g.fillRect(2 + j * Game.ONE_UNIT_SIZE, 24 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                        g.setColor(Color.black);
                        g.drawRect(2 + j * Game.ONE_UNIT_SIZE, 24 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                    else if (Board.get(j, i) == GameUnit.SNAKE) {
                        g.setColor(Color.green);
                        g.fillRect(2 + j * Game.ONE_UNIT_SIZE, 24 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                    else if (Board.get(j, i) == GameUnit.EMPTY_SPACE) {
                        g.setColor(new Color(150, 75, 0));
                        g.fillRect(2 + j * Game.ONE_UNIT_SIZE, 24 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                }
            }
            initialised = true;
        }

        if (!gameEnded) {

            g.setColor(new Color(150, 75, 0));
            g.fillRect(2 + snake.getLastPartLastSeen().getX() * Game.ONE_UNIT_SIZE,
                    24 + snake.getLastPartLastSeen().getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);
            g.setColor(Color.green);

            g.fillRect(2 + snake.get(1).getX() * Game.ONE_UNIT_SIZE,
                    24 + snake.get(1).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.fillRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE,
                    24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.fillRect(2 + snake.get(snake.getLength() - 1).getX() * Game.ONE_UNIT_SIZE,
                    24 + snake.get(snake.getLength() - 1).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.setColor(Color.black);
            if (snake.getDirection() == Direction.UP) {
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 3, 2, 2);
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 3, 2, 2);
            }
            else if (snake.getDirection() == Direction.RIGHT) {
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 2, 2, 2);
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
            }
            else if (snake.getDirection() == Direction.DOWN) {
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
            }
            else if (snake.getDirection() == Direction.LEFT) {
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
                g.drawRect(2 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        24 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 2, 2, 2);
            }

        }


        if (Board.foodExists)
        {
            g.setColor(Color.red);
            g.fillRect(2 + food.getX() * Game.ONE_UNIT_SIZE, 24 + food.getY() * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
        }

    }



}
