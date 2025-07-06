package com.javarush.games;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;


@RequiredArgsConstructor
public class Layer extends JPanel {
    private final Room game;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Задаем цвет элементов зеленый
        g.setColor(GREEN);
        //Рисуем прямоугольник показывающий край поля справа
        g.fillRect(game.getWidth() * 10, 0, 10, (game.getWidth() * 10) + 10);
        //Рисуем прямоугольник показывающий край поля снизу
        g.fillRect(0, game.getHeight() * 10, (game.getHeight() * 10) + 10, 10);
        //Рисуем прямоугольник показывающий мышь
        g.fillRect(game.getMouse().x() * 10, game.getMouse().y() * 10, 10, 10);

        //Задаем цвет элементов змейки
        g.setColor(ORANGE);
        //Рисуем по очереди секции змейки
        for (SnakeSection snakeSection : game.getSnake().getSections()) {
            g.fillRect(snakeSection.x() * 10, snakeSection.y() * 10, 10, 10);
        }
    }
}
