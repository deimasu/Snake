package ru.xc0re.games.snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame implements Runnable {

    private Snake snake;
    private Board board;
    private Food food;

    private boolean gameEnded;
    private boolean initialised;

    public static final int ONE_UNIT_SIZE = 15;
    public static final int HEIGHT = Board.HEIGHT * ONE_UNIT_SIZE + 39;
    public static final int WIDTH = Board.WIDTH * ONE_UNIT_SIZE + 16;

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
                        snake.setDirection(3);
                }
                else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
                {
                    if (snake.get(1).getX() != snake.get(0).getX() + 1)
                        snake.setDirection(1);
                }
                else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP)
                {
                    if (snake.get(1).getY() != snake.get(0).getY() - 1)
                        snake.setDirection(0);
                }
                else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
                {
                    if (snake.get(1).getY() != snake.get(0).getY() + 1)
                        snake.setDirection(2);
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
            for (int i = 0; i < Board.WIDTH; i++) {
                for (int j = 0; j < Board.HEIGHT; j++) {
                    if (Board.get(j, i) == 1) {
                        g.setColor(Color.gray);
                        g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                        g.setColor(Color.black);
                        g.drawRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                    else if (Board.get(j, i) == 2) {
                        g.setColor(Color.green);
                        g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                    else if (Board.get(j, i) == 0) {
                        g.setColor(new Color(150, 75, 0));
                        g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                    }
                }
            }
            initialised = true;
        }

        if (!gameEnded) {

            g.setColor(new Color(150, 75, 0));
            g.fillRect(8 + snake.getLastPartLastSeen().getX() * Game.ONE_UNIT_SIZE,
                    30 + snake.getLastPartLastSeen().getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);
            g.setColor(Color.green);

            g.fillRect(8 + snake.get(1).getX() * Game.ONE_UNIT_SIZE,
                    30 + snake.get(1).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.fillRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE,
                    30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.fillRect(8 + snake.get(snake.getLength() - 1).getX() * Game.ONE_UNIT_SIZE,
                    30 + snake.get(snake.getLength() - 1).getY() * Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE,
                    Game.ONE_UNIT_SIZE);

            g.setColor(Color.black);
            if (snake.getDirection() == 0) {
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 3, 2, 2);
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 3, 2, 2);
            }
            else if (snake.getDirection() == 1) {
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 2, 2, 2);
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
            }
            else if (snake.getDirection() == 2) {
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 10,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
            }
            else if (snake.getDirection() == 3) {
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 10, 2, 2);
                g.drawRect(8 + snake.get(0).getX() * Game.ONE_UNIT_SIZE + 2,
                        30 + snake.get(0).getY() * Game.ONE_UNIT_SIZE + 2, 2, 2);
            }

        }


        if (Board.foodExists)
        {
            g.setColor(Color.red);
            g.fillRect(8 + food.getX() * Game.ONE_UNIT_SIZE, 30 + food.getY() * Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
        }

    }



}
