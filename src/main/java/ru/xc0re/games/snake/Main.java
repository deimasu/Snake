package ru.xc0re.games.snake;

public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(new Game());

        thread.start();

    }

}
