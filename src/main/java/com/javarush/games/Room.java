package com.javarush.games;

import lombok.Getter;

import java.awt.event.KeyEvent;

import static com.javarush.games.SnakeDirection.*;
import static java.awt.event.KeyEvent.*;


@Getter
public class Room {
    private final int width;
    private final int height;
    private final int initialDelay = 400;
    private final int delayStep = 25;
    private final Snake snake;
    private Mouse mouse;
    private int score;
    private static Room game;
    private KeyboardObserver keyboardObserver;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        //Создаем поток "наблюдатель за клавиатурой" и стартуем его.
        keyboardObserver = new KeyboardObserver(game);
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                switch (event.getKeyCode()) {
                    case VK_LEFT -> snake.setDirection(LEFT);
                    case VK_RIGHT -> snake.setDirection(RIGHT);
                    case VK_UP -> snake.setDirection(UP);
                    case VK_DOWN -> snake.setDirection(DOWN);
                }
            }

            snake.move();   //двигаем змею
            print();        //отображаем текущее состояние игры
            sleep();        //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
        System.out.println("You score is: " + score);
    }

    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
        if (keyboardObserver.getFrame() != null) {
            keyboardObserver.getFrame().setContentPane(new Layer(this));
            keyboardObserver.getFrame().setVisible(true);
        }
    }

    /**
     * Метод вызывается, когда мышь съели
     */
    public void eatMouse() {
        createMouse();
        score++;
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }

    /**
     * Программа делает паузу, длительность зависит от длины змеи.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay;
            if (level < 12) {
                delay = initialDelay - delayStep * level;
            } else {
                delay = 75; // минимальная задержка
            }
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(DOWN);
        game.snake.setGame(game);
        game.createMouse();
        game.run();
    }
}
