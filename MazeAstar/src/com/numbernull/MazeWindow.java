package com.numbernull;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;


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
        if(beginX != -1 && beginY != -1) {
            mazeField.elementAt(beginY).elementAt(beginX).setBackground(Color.YELLOW);
            mazeField.elementAt(beginY).elementAt(beginX).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        }
        if(endX != -1 && endY != -1) {
            mazeField.elementAt(endY).elementAt(endX).setBackground(Color.BLUE);
            mazeField.elementAt(endY).elementAt(endX).setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        }
    }


    public MazeWindow(String s, int x, int y, Maze labyrinth) {
        super(s);

        this.path = new ArrayList<>();
        this.currentIteration = 0;

        beginX = -1;
        beginY = -1;
        endX = -1;
        endY = -1;

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Container container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        GridLayout mazeStyle = new GridLayout(x, y, 0, 0);
        JPanel maze = new JPanel();
//        maze.setSize(600, 600);
        maze.setLayout(mazeStyle);

        // дизайн toolbara
        GridBagLayout optionsStyle = new GridBagLayout();
        JPanel options = new JPanel();
        options.setLayout(optionsStyle);
        GridBagConstraints layoutOpt = new GridBagConstraints();
        layoutOpt.insets = new Insets(20,20,20,20);
        options.setBackground(Color.WHITE);

        JButton start = new JButton("Старт");
        start.setFont(new java.awt.Font("Lucida Grande", 1, 28));

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(beginX == -1 || beginY == -1 || endX == -1 || endY == -1 ){
                    JOptionPane.showMessageDialog(new JFrame(), "Укажите \"Начало\" и \"Конец\"",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else if(!isBegin && !isEnd) {
                    if (e.getSource() == start && !isReady) {
                        path = Astar.search(labyrinth, labyrinth.labyrinth.elementAt(beginY).elementAt(beginX), labyrinth.labyrinth.elementAt(endY).elementAt(endX));
                        isReady = true;
                        updateField(Astar.snap.states.get(Astar.snap.states.size() - 1));
                        currentIteration = Astar.snap.states.size() - 1;
                    }else{
                        if(e.getSource() == start && isReady){
                            currentIteration = Astar.snap.states.size() - 1;
                            updateField(Astar.snap.states.get(Astar.snap.states.size() - 1));
                        }
                    }
                }
            }
        });

        layoutOpt.fill = GridBagConstraints.HORIZONTAL;
        layoutOpt.gridx = 0; // № столбца
        layoutOpt.gridy = 0; // № строки
        layoutOpt.gridwidth = 2; // число ячеек, занимаемых объектом
        layoutOpt.ipadx = 70;
        layoutOpt.ipady = 40;
        layoutOpt.anchor = GridBagConstraints.CENTER; //  задает выравнивание

        options.add(start, layoutOpt);

        JButton next = new JButton("Вперед");
        JButton prev = new JButton("Назад");

        next.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        prev.setFont(new java.awt.Font("Lucida Grande", 1, 18));

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == next) {
                    if(beginX == -1 || beginY == -1 || endX == -1 || endY == -1 ){
                        JOptionPane.showMessageDialog(new JFrame(), "Укажите \"Начало\" и \"Конец\"",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else  if(!isBegin && !isEnd) {
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
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == prev){
                    if(beginX == -1 || beginY == -1 || endX == -1 || endY == -1 ){
                        JOptionPane.showMessageDialog(new JFrame(), "Укажите \"Начало\" и \"Конец\"",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }else  if(!isBegin && !isEnd){
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
            }
        });

        layoutOpt.fill = GridBagConstraints.HORIZONTAL;
        layoutOpt.gridx = 0; // № столбца
        layoutOpt.gridy = 1; // № строки
        layoutOpt.gridwidth = 1; // число ячеек, занимаемых объектом
        layoutOpt.anchor = GridBagConstraints.LINE_END; //  задает выравнивание

        options.add(prev, layoutOpt);
        layoutOpt.gridx = 1; // № столбца
        layoutOpt.anchor = GridBagConstraints.LINE_START;
        options.add(next, layoutOpt);

        // настройки для стороны с лабиринтом
        JPanel mazePane = new JPanel();
        mazePane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mazePane.setLayout(new GridBagLayout());
        GridBagConstraints gbcMaze = new GridBagConstraints();
        gbcMaze.fill = GridBagConstraints.NONE; //стратегию распределения компоненту свободного пространства
        gbcMaze.weightx = 1; //выделение пространства для столбцов

        gbcMaze.gridx = 0; // № столбца
        gbcMaze.gridy = 0; // № строки
        gbcMaze.gridwidth = 1; // число ячеек, занимаемых объектом

//        gbcMaze.ipadx = 20;
//        gbcMaze.ipady = 20;
//        gbcMaze.anchor = GridBagConstraints.LINE_END; //  задает выравнивание компонента внутри отведенного для него пространства
//        leftPane.add(new JButton("Сохранить лабиринт"), gbcMaze);
//
//        gbcMaze.gridx = 1; // № столбца
//        gbcMaze.gridy = 0; // № строки
//        gbcMaze.gridwidth = 1; // число ячеек, занимаемых объектом
//        gbcMaze.anchor = GridBagConstraints.LINE_START;
//        leftPane.add(new JButton("Загрузить лабиринт"), gbcMaze);

        gbcMaze.ipadx = 0;
        gbcMaze.ipady = 0;
        gbcMaze.fill = GridBagConstraints.BOTH;
        gbcMaze.gridwidth = 2;
        gbcMaze.weighty = 1; //и строк
        gbcMaze.gridy = 1; // № строки


        JButton setBegin = new JButton("Указать начало");
        JButton setEnd = new JButton("Указать конец");

        setBegin.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        setEnd.setFont(new java.awt.Font("Lucida Grande", 1, 18));

        setBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setBegin) {
                    if(isReady){
                        cleanTable(labyrinth,endX, endY);
                    }
                    isBegin = true;
                    isEnd = false;
                    setBegin.setEnabled(false);
                    setEnd.setEnabled(true);
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
                    setEnd.setEnabled(false);
                    setBegin.setEnabled(true);
                }
            }
        });

        layoutOpt.gridx = 0; // № столбца
        layoutOpt.gridy = 2;
        layoutOpt.anchor = GridBagConstraints.LINE_START;
        options.add(setBegin, layoutOpt);
        layoutOpt.gridx = 1;
        layoutOpt.anchor = GridBagConstraints.LINE_END;
        options.add(setEnd, layoutOpt);

        mazeField = new Vector<>();


        for (int i = 0; i < y; i++) {
            Vector<JLabel> cells = new Vector<>();
            for (int j = 0; j < x; j++) {
                if (!labyrinth.labyrinth.elementAt(i).elementAt(j).isWall) {
                    JLabel textLabel = new JLabel();
                    textLabel.setFont(new java.awt.Font("Lucida Grande", 1, 0));
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
                                mazeField.elementAt(beginY).elementAt(beginX).setBackground(Color.YELLOW);
                                mazeField.elementAt(beginY).elementAt(beginX).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
                                setBegin.setEnabled(true);
                                updateField(labyrinth);
                            }
                            if (isEnd) {
                                endX = x;
                                endY = y;
                                isEnd = false;
                                mazeField.elementAt(endY).elementAt(endX).setBackground(Color.BLUE);
                                mazeField.elementAt(endY).elementAt(endX).setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
                                setEnd.setEnabled(true);
                                updateField(labyrinth);
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

        // start of legend 150+ строк легенды
        GridBagLayout usabilityStyle = new GridBagLayout();
        JPanel usabilityPane = new JPanel();
        usabilityPane.setLayout(usabilityStyle);
        GridBagConstraints usabilityOpt = new GridBagConstraints();


        usabilityOpt.fill = GridBagConstraints.NONE;
        usabilityOpt.gridwidth = 1; // число ячеек, занимаемых объектом
        usabilityOpt.anchor = GridBagConstraints.CENTER; //  задает выравнивание

        // green
        usabilityOpt.gridx = 0; // № столбца
        usabilityOpt.gridy = 1; // № строки
        usabilityOpt.insets = new Insets(40, 20, 0, 30); // отступ у элемента (w, a, s, d)
        JLabel tmp = new JLabel();
        tmp.setBackground(Color.GREEN);
        tmp.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 20, 0, 30);
        usabilityOpt.gridx = 0; // № столбца
        usabilityOpt.gridy = 2;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("дорожка");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);
        // magneta
        usabilityOpt.insets = new Insets(40, 20, 0, 30);
        usabilityOpt.gridy = 3;
        tmp = new JLabel();
        tmp.setBackground(Color.MAGENTA);
        tmp.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 20, 0, 30);
        usabilityOpt.gridy = 4;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("потенциальный путь");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        // red
        usabilityOpt.insets = new Insets(40, 0, 0, 20);
        usabilityOpt.gridx = 1; // № столбца
        usabilityOpt.gridy = 1;
        tmp = new JLabel();
        tmp.setBackground(Color.RED);
        tmp.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 0, 0, 20);
        usabilityOpt.gridy = 2;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("искомый путь");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        // black
        usabilityOpt.insets = new Insets(40, 0, 0, 20);
        usabilityOpt.gridy = 3;
        tmp = new JLabel();
        tmp.setBackground(Color.BLACK);
        tmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 0, 0, 20);
        usabilityOpt.gridy = 4;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("стена");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        //yellow
        usabilityOpt.insets = new Insets(40, 0, 0, 20);
        usabilityOpt.gridx = 2; // № столбца
        usabilityOpt.gridy = 1;
        tmp = new JLabel();
        tmp.setBackground(Color.YELLOW);
        tmp.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 0, 0, 20);
        usabilityOpt.gridy = 2;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("начало");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        //blue
        usabilityOpt.insets = new Insets(40, 0, 0, 20);
        usabilityOpt.gridy = 3;
        tmp = new JLabel();
        tmp.setBackground(Color.BLUE);
        tmp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setPreferredSize(new Dimension(120,20));
        usabilityPane.add(tmp, usabilityOpt);

        usabilityOpt.insets = new Insets(0, 0, 0, 20);
        usabilityOpt.gridy = 4;
        tmp = new JLabel();
        tmp.setBackground(Color.WHITE);
        tmp.setOpaque(true);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setText("конец");
        tmp.setFont(new java.awt.Font("Lucida Grande", 1, 15));
        usabilityPane.add(tmp, usabilityOpt);

        // в начало layout добавляем поле с кнопками
        usabilityOpt.gridx = 0;
        usabilityOpt.gridy = 0;
        usabilityOpt.gridwidth = GridBagConstraints.REMAINDER;
        usabilityPane.add(options, usabilityOpt);

        mazePane.add(maze, gbcMaze);

        setBackground(Color.WHITE);
        mazePane.setBackground(Color.WHITE);
        usabilityPane.setBackground(Color.WHITE);


        container.add(mazePane);
        container.add(usabilityPane);


    }
    private  void cleanTable(Maze m, int saveX, int saveY){
        m.clear();
        isReady = false;
        currentIteration = 0;
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