package com.numbernull;

import javax.swing.*;
import java.util.List;



/**TODO
 * Анимация нажатости.
 * меньше кнопки и растояние между ними, подвинуть их
 * в оставшееся место вставить легенду цветов
 * ? сохранение - загрузка
 *
 * пустой - либо выбрана стена, либо нет пути.
 *
 *
 * тест по касоте
 *
 * вперед делает все сразу
 *без выбора назначения - исключение. V
 * при выборе новой вершины clear(my) V
 * при вперед назад тоже
 * закрашивать старт и финиш V
 *
 */
public class Main {

    public static void main(String[] args) {
//    	Maze M = new Maze(50, 50);
//    	M.printMaze();
//		List<Maze.Cell> d = DFS.search(M,M.labyrinth.elementAt(1).elementAt(1),
//				M.labyrinth.elementAt(8).elementAt(8));
//		List<Maze.Cell> a = Astar.search(M,M.labyrinth.elementAt(1).elementAt(1),
//				M.labyrinth.elementAt(8).elementAt(8));
//
//		System.out.format("d: %d \na: %d\n", d.size(), a.size());
//		for (int i = 0; i < a.size() ; i++) {
//			System.out.format("%d: xd = %d, yd = %d || xa = %d, ya = %d \n",
//					a.size()-i, d.get(i).x,d.get(i).y, a.get(i).x, a.get(i).y);
//		}

		SizeInput si = new SizeInput("Maze");
		si.setVisible(true);
		si.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		si.setSize(300, 200);
		si.setResizable(false);
		si.setLocationRelativeTo(null);
		si.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

	}

}
