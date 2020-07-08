package com.numbernull;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
		SizeInput si = new SizeInput("Maze");
		si.setVisible(true);
		si.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		si.setSize(300, 200);
		si.setResizable(false);
		si.setLocationRelativeTo(null);
		si.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	}
}
