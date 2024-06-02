package com.javarush.games;

import javax.swing.JPanel;
import java.awt.Graphics;

import static com.javarush.games.Room.game;
import static java.awt.Color.*;


public class Layer extends JPanel {
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
        g.fillRect(game.getMouse().getX() * 10, game.getMouse().getY() * 10, 10, 10);

        //Задаем цвет элементов змейки
        g.setColor(ORANGE);
        //Рисуем по очереди секции змейки
        for (SnakeSection snakeSection : game.getSnake().getSections()) {
            g.fillRect(snakeSection.getX() * 10, snakeSection.getY() * 10, 10, 10);
        }
    }
}
