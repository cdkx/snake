package com.javarush.games;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.javarush.games.Room.game;
import static com.javarush.games.SnakeDirection.*;


public class Snake {
    @Setter
    private SnakeDirection direction;
    @Getter
    private final List<SnakeSection> sections;
    @Getter
    private boolean isAlive;


    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public SnakeSection getHead() {
        return sections.get(0);
    }

    /**
     * Метод перемещает змею на один ход.
     * Направление перемещения задано переменной direction.
     */
    public void move() {
        if (!isAlive) {
            return;
        }

        if (this.direction == UP) {
            move(0, -1);
        } else if (this.direction == RIGHT) {
            move(1, 0);
        } else if (this.direction == DOWN) {
            move(0, 1);
        } else if (this.direction == LEFT) {
            move(-1, 0);
        }
    }

    /**
     * Метод перемещает змею в соседнюю клетку.
     * Координаты клетки заданы относительно текущей головы с помощью переменных (dx, dy).
     */
    private void move(int dx, int dy) {
        // Создаем новую голову
        SnakeSection newHead = new SnakeSection(getHead().getX() + dx, getHead().getY() + dy);

        // Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(newHead);
        if (!isAlive) {
            return;
        }

        // Проверяем - не пересекает ли змея  саму себя
        checkBody(newHead);
        if (!isAlive) {
            return;
        }

        sections.add(0, newHead);
        if ((newHead.getX() == game.getMouse().getX())
            && (newHead.getY() == game.getMouse().getY())) {
            game.eatMouse();
        } else {
            sections.remove(sections.size() - 1);
        }
    }

    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    private void checkBorders(SnakeSection head) {
        if (((head.getX() < 0)
             || (head.getX() >= game.getWidth()))
            || (head.getY() < 0)
            || (head.getY() >= game.getHeight())) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
