package com.numbernull;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MazeWindow extends JFrame {


    public MazeWindow(String s, int x, int y, Maze labyrinth){
        super(s);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Container container = getContentPane();
        container.setLayout(new GridLayout(0,2));

        GridLayout mazeStyle = new GridLayout(x, y, 0, 0);
        JPanel maze = new JPanel();
        maze.setSize(400, 400);
        maze.setLayout(mazeStyle);

        FlowLayout optionsStyle = new FlowLayout(FlowLayout.CENTER);
        JPanel options = new JPanel();
        options.setLayout(optionsStyle);

        options.add(new JButton("Старт"));
        options.add(new JButton("Вперед"));
        options.add(new JButton("Назад"));
        options.add(new JButton("Сохранить лабиринт"));
        options.add(new JButton("Загрузить лабиринт"));
        options.add(new JButton("Указать начало"));
        options.add(new JButton("Указать конец"));

        for(int i = 0; i < y; i++)
            for(int j = 0; j < x; j++) {
                if(!labyrinth.labyrinth.elementAt(i).elementAt(j).isWall) {
                    JLabel textLabel = new JLabel();
                    textLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
                    textLabel.setBackground(Color.GREEN);
                    textLabel.setOpaque(true);
                    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    textLabel.setPreferredSize(new Dimension(10, 10));//исправить
                    maze.add(textLabel);
                }else{
                    JLabel textLabel = new JLabel();
                    textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                    textLabel.setBackground(Color.BLACK);
                    textLabel.setOpaque(true);
                    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    textLabel.setPreferredSize(new Dimension(10, 10));//исправить
                    maze.add(textLabel);
                }
            }
        container.add(maze);
        container.add(options);

    }
}
