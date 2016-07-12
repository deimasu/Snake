package ru.xc0re.games.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends JFrame implements Runnable {

    private Snake snake;
    private Board board;
    private Food food;

    private boolean gameEnded;

    public static final int ONE_UNIT_SIZE = 19;
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
        setResizable(true);
        setVisible(true);



    }

    public void run() {

        addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                myKeyEvt(e, "keyTyped");
            }


            public void keyReleased(KeyEvent e) {
                myKeyEvt(e, "keyReleased");
            }


            public void keyPressed(KeyEvent e) {
                myKeyEvt(e, "keyPressed");
            }

            private void myKeyEvt(KeyEvent e, String text) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
                {
                    if (snake.getDirection() != 1)
                        snake.setDirection(3);
                }
                else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
                {
                    if (snake.getDirection() != 3)
                        snake.setDirection(1);
                }
                else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP)
                {
                    if (snake.getDirection() != 2)
                        snake.setDirection(0);
                }
                else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
                {
                    if (snake.getDirection() != 0)
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

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



    public void paint(Graphics g) {
        g.setColor(new Color(150, 75, 0));


        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (Board.get(j, i) == 1) {
                    g.setColor(Color.gray);
                    g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE,  Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                }
                else if (Board.get(j, i) == 0) {
                    g.setColor(new Color(150, 75, 0));
                    g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE,  Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                }
                else if (Board.get(j, i) == 2) {
                    g.setColor(Color.green);
                    g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE,  Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                }
                else if (Board.get(j, i) == 3) {
                    g.setColor(Color.red);
                    g.fillRect(8 + j * Game.ONE_UNIT_SIZE, 30 + i * Game.ONE_UNIT_SIZE,  Game.ONE_UNIT_SIZE, Game.ONE_UNIT_SIZE);
                }
            }
        }
    }



}
