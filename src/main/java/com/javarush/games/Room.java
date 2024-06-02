package com.javarush.games;

import lombok.Getter;

import java.awt.event.KeyEvent;

import static com.javarush.games.SnakeDirection.*;
import static java.awt.event.KeyEvent.*;


public class Room {
    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final Snake snake;
    @Getter
    private Mouse mouse;

    public static Room game;
    private int score;
    private final int initialDelay = 350;
    private final int delayStep = 25;

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
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == VK_LEFT)
                    snake.setDirection(LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == VK_RIGHT)
                    snake.setDirection(RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == VK_UP)
                    snake.setDirection(UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == VK_DOWN)
                    snake.setDirection(DOWN);
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
        if (KeyboardObserver.frame != null) {
            KeyboardObserver.frame.setContentPane(new Layer());
            KeyboardObserver.frame.setVisible(true);
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
            if (level < 16) {
                delay = initialDelay - delayStep * level;
            } else {
                delay = 100; // минимальная задержка
            }
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(DOWN);
        game.createMouse();
        game.run();
    }
}
