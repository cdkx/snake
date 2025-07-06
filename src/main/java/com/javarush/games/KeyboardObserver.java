package com.javarush.games;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


@Getter
public class KeyboardObserver extends Thread {
    private final JFrame frame;
    private final Queue<KeyEvent> keyEvents;
    private final Room game;

    public KeyboardObserver(Room game) {
        this.frame = new JFrame();
        this.keyEvents = new ArrayBlockingQueue<>(100);
        this.game = game;
    }


    @Override
    public void run() {
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(false);
        frame.setSize((game.getWidth() * 10) + 17, (game.getHeight() * 10) + 40);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

        frame.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //do nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                Runtime.getRuntime().exit(0);
            }
        });

        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                //do nothing
            }

            public void keyReleased(KeyEvent e) {
                //do nothing
            }

            public void keyPressed(KeyEvent e) {
                keyEvents.add(e);
            }
        });
    }

    public boolean hasKeyEvents() {
        return !keyEvents.isEmpty();
    }

    public KeyEvent getEventFromTop() {
        return keyEvents.poll();
    }
}
