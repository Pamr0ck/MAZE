package com.numbernull;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * TO-DO exceptions
 */

public class MazeWindow extends JFrame {

    boolean isBegin = false;
    boolean isEnd = false;
    boolean isReady = false;

    int beginX;
    int beginY;
    int endX;
    int endY;
    Vector<Vector<JLabel>> mazeField;
    ArrayList<Maze.Cell> path;

    int currentIteration;

    private void updateField(Maze maze) {
        for (int i = 1; i < maze.sizeY - 1; i++) {
            for (int j = 1; j < maze.sizeX - 1; j++) {
                if (maze.labyrinth.elementAt(i).elementAt(j).wasSeen) {
                    boolean onWay = false;
                    for(Maze.Cell tmp : path){
                        if(tmp.x == j && tmp.y == i){
                            onWay = true;
                            break;
                        }
                    }
                    if(onWay){
                        mazeField.elementAt(i).elementAt(j).setBackground(Color.RED);
                        mazeField.elementAt(i).elementAt(j).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    }else {
                        mazeField.elementAt(i).elementAt(j).setBackground(Color.MAGENTA);
                        mazeField.elementAt(i).elementAt(j).setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 1));
                    }
                }else{
                    if(!maze.labyrinth.elementAt(i).elementAt(j).isWall) {
                        mazeField.elementAt(i).elementAt(j).setBackground(Color.GREEN);
                        mazeField.elementAt(i).elementAt(j).setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                    }
                }
            }
        }
    }


    public MazeWindow(String s, int x, int y, Maze labyrinth) {
        super(s);

        this.path = new ArrayList<>();
        this.currentIteration = 0;

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Container container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        GridLayout mazeStyle = new GridLayout(x, y, 0, 0);
        JPanel maze = new JPanel();
        //maze.setSize(1000, 600);
        maze.setLayout(mazeStyle);

        FlowLayout optionsStyle = new FlowLayout(FlowLayout.CENTER);
        JPanel options = new JPanel();
        options.setLayout(optionsStyle);

        JButton start = new JButton("Старт");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == start && !isReady) {
                    path = Astar.search(labyrinth, labyrinth.labyrinth.elementAt(beginY).elementAt(beginX), labyrinth.labyrinth.elementAt(endY).elementAt(endX));
                    isReady = true;
                    updateField(Astar.snap.states.get(Astar.snap.states.size() - 1));
                    currentIteration = Astar.snap.states.size() - 1;
                }
            }
        });

        options.add(start);
        JButton next = new JButton("Вперед");
        JButton prev = new JButton("Назад");

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == next) {
                    if(!isReady) {
                        path = Astar.search(labyrinth, labyrinth.labyrinth.elementAt(beginY).elementAt(beginX), labyrinth.labyrinth.elementAt(endY).elementAt(endX));
                        isReady = true;
                    }
                    if(currentIteration < Astar.snap.states.size() - 1) {
                        currentIteration++;
                        updateField(Astar.snap.states.get(currentIteration));
                    }
                }
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == prev){
                    if (!isReady) {
                        path = Astar.search(labyrinth, labyrinth.labyrinth.elementAt(beginY).elementAt(beginX), labyrinth.labyrinth.elementAt(endY).elementAt(endX));
                        isReady = true;
                    }
                    if (currentIteration > 0) {
                        currentIteration--;
                        updateField(Astar.snap.states.get(currentIteration));
                    }
                }
            }
        });


        options.add(next);
        options.add(prev);



        options.add(new JButton("Сохранить лабиринт"));
        options.add(new JButton("Загрузить лабиринт"));

        JButton setBegin = new JButton("Указать начало");
        JButton setEnd = new JButton("Указать конец");

        setBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setBegin) {
                    if(isReady){
                        cleanTable(labyrinth,endX, endY);
                    }
                    isBegin = true;
                    isEnd = false;
                }
            }
        });

        setEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setEnd) {
                    if(isReady){
                        cleanTable(labyrinth, beginX, beginY);
                    }
                    isEnd = true;
                    isBegin = false;
                }
            }
        });


        options.add(setBegin);
        options.add(setEnd);

        mazeField = new Vector<>();


        for (int i = 0; i < y; i++) {
            Vector<JLabel> cells = new Vector<>();
            for (int j = 0; j < x; j++) {
                if (!labyrinth.labyrinth.elementAt(i).elementAt(j).isWall) {
                    JLabel textLabel = new JLabel();
                    textLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                    textLabel.setBackground(Color.GREEN);
                    textLabel.setOpaque(true);
                    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    textLabel.setText(j + " " + i);
                    textLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int x;
                            int y;
                            String[] args = textLabel.getText().split(" ");
                            x = Integer.parseInt(args[0]);
                            y = Integer.parseInt(args[1]);
                            System.out.println("\tx = " + x + " y = " + y);
                            if (isBegin) {
                                beginX = x;
                                beginY = y;
                                isBegin = false;
                            }
                            if (isEnd) {
                                endX = x;
                                endY = y;
                                isEnd = false;
                            }
                        }
                    });
                    cells.addElement(textLabel);
                    maze.add(textLabel);
                } else {
                    JLabel textLabel = new JLabel();
                    textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                    textLabel.setBackground(Color.BLACK);
                    textLabel.setOpaque(true);
                    textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    cells.addElement(textLabel);
                    maze.add(textLabel);
                }
            }
            mazeField.addElement(cells);
        }


        container.add(maze);
        container.add(options);


    }
    private  void cleanTable(Maze m, int saveX, int saveY){
        m.clear();
        isReady = false;
        for (int i = 0; i < m.sizeY-1; i++) {
            for (int j = 0; j < m.sizeX-1 ; j++) {
                if(!m.labyrinth.elementAt(i).elementAt(j).isWall && !(i == saveY && j == saveX)){
                    mazeField.elementAt(i).elementAt(j).setBackground(Color.GREEN);
                    mazeField.elementAt(i).elementAt(j).setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                }
            }
        }
    }
}