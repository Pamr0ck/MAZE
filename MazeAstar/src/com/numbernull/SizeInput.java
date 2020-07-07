package com.numbernull;

import org.w3c.dom.ranges.RangeException;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SizeInput extends JFrame{
    private final JTextField yCord;
    private int y = 0 ;
    private final JButton ok;

    public SizeInput(final String s) {
        super(s);
        setLayout(new GridLayout(3, 0, 10, 10));
        this.ok = new JButton("Ok");
        JLabel info = new JLabel("Введите размер:");
        this.yCord = new JTextField("длина лабиринта", 5);
        add(info);
        add(this.yCord);
        add(this.ok);

        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == ok){
                    try {
                        y = Integer.parseInt(yCord.getText());
                        if (y < 4 || y > 100) {
                            throw new RangeException((short) 0,"");
                        }
                        setVisible(false);
                        new MazeWindow(s, y, y, new Maze(y, y));
                    }catch (NumberFormatException | RangeException err){
                        JOptionPane.showMessageDialog(new JFrame(), "Введите целое значение от 4 до 100",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        yCord.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                yCord.setText("");
            }
        });

    }
}
