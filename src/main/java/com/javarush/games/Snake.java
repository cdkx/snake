package com.javarush.games;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Snake {
    private final List<SnakeSection> sections;
    private SnakeDirection direction;
    private boolean isAlive;
    private Room game;


    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
        direction = SnakeDirection.DOWN;
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

        switch (this.direction) {
            case UP -> move(0, -1);
            case RIGHT -> move(1, 0);
            case DOWN -> move(0, 1);
            case LEFT -> move(-1, 0);
        }
    }

    /**
     * Метод перемещает змею в соседнюю клетку.
     * Координаты клетки заданы относительно текущей головы с помощью переменных (dx, dy).
     */
    private void move(int dx, int dy) {
        // Создаем новую голову
        SnakeSection newHead = new SnakeSection(getHead().x() + dx, getHead().y() + dy);

        // Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(newHead);
        // Проверяем - не пересекает ли змея саму себя
        checkBody(newHead);
        if (!isAlive) {
            return;
        }

        sections.add(0, newHead);
        if ((newHead.x() == game.getMouse().x()) && (newHead.y() == game.getMouse().y())) {
            game.eatMouse();
        } else {
            sections.remove(sections.size() - 1);
        }
    }

    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    private void checkBorders(SnakeSection head) {
        if (((head.x() < 0) || (head.x() >= game.getWidth())) || (head.y() < 0) || (head.y() >= game.getHeight())) {
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
