package com.numbernull;

import java.util.*;

public class Maze {
    public final int sizeX;
    public final int sizeY;
    public final Vector<Vector<Cell>> labyrinth;
    public Maze(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        labyrinth = new Vector< Vector<Cell> >(10, 100);
        for(int i = 0; i < sizeY; i++) {
            Vector<Cell> cells = new Vector<Cell>();
            for (int j = 0; j < sizeX; j++) {
                Cell cell = new Cell(j, i);
                cells.addElement(cell);
                cell.isWall = true;
            }
            labyrinth.addElement(cells);
        }

        for(int i = 1; i < sizeY - 1; i++){
            for(int j = 1; j < sizeX - 1; j++){
                addNeighbours(labyrinth.elementAt(i).elementAt(j));
            }
        }
        generateMaze();
    }
    public Maze(int size){
        this.sizeX = size;
        this.sizeY = size;
        labyrinth = new Vector< Vector<Cell> >(10, 10);
        for(int i = 0; i < sizeY; i++) {
            Vector<Cell> cells = new Vector<Cell>();
            for (int j = 0; j < sizeX; j++) {
                Cell cell = new Cell(j, i);
                cells.addElement(cell);
                cell.isWall = true;
            }
            labyrinth.addElement(cells);
        }
        for(int i = 1; i < sizeY - 1; i++){
            for(int j = 1; j < sizeX - 1; j++){
                addNeighbours(labyrinth.elementAt(i).elementAt(j));
            }
        }
    }

    public Maze(Maze m){
        this.sizeX = m.sizeX;
        this.sizeY = m.sizeY;
        this.labyrinth = new Vector< Vector<Cell> >();
        for(int i = 0; i < sizeY; i++) {
            Vector<Cell> cells = new Vector<Cell>();
            for (int j = 0; j < sizeX; j++) {
                Cell cell = new Cell(m.labyrinth.elementAt(i).elementAt(j));
                cells.addElement(cell);
            }
            this.labyrinth.addElement(cells);
        }
    }

    public void printMaze(){
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                if(labyrinth.elementAt(i).elementAt(j).isWall){
                    System.out.print("#");
                }else System.out.print("&");
            }
            System.out.print("\n");
        }
        System.out.println();
    }
    private void generateMaze() {
        Cell currentCell = labyrinth.elementAt(1).elementAt(1);
        Stack<Cell> stack = new Stack<Cell>();
        Map<Cell, Integer> proceccedCells = new HashMap<Cell, Integer>();
        stack.push(currentCell);
        boolean flagStack = false;
        //основной цикл
        while (!stack.empty()) {
            proceccedCells.put(currentCell, -1);//если вершина является дорожкой, то нет смысла в нее ходить
            currentCell.isWall = false;//ставим в текущую клетку путь
            //записываем соседей просмотриваемой вершины в массив, и помечаем сколько раз !повторно! мы их встретили
            if(!flagStack) {
                for (Cell i : currentCell.neighbours) {
                    if (proceccedCells.containsKey(i)) {
                        if (proceccedCells.get(i) != -1) {
                            proceccedCells.put(i, proceccedCells.get(i) + 1);
                        }
                    } else {
                        proceccedCells.put(i, 0);
                    }
                }
            }
            Random random = new Random();
            //выбираем рандомного соседа
            Cell randNeighbour = currentCell.neighbours.elementAt(random.nextInt(currentCell.neighbours.size()));

            boolean backFlag = false;
            //если рандомный сосед уже сосед другой просмотренной вершины
            if (proceccedCells.get(randNeighbour) != 0) {
                //проверяем, что есть сосед, который не принадлежит обработанной вершине
                for (Cell i : currentCell.neighbours) {
                    backFlag = proceccedCells.get(i) != 0;//false - когда равно 0, то есть потенциальная дорожка
                    if(!backFlag){
                        break;
                    }
                }
            }
            //если таких вершин нет, тот идем на предыдущую ячейку
            if (backFlag) {
                //смотрим, что не пытаемся очистить пустой стек, и переходим в предыдущую вершину
                stack.pop();
                if(!stack.empty()) {
                    currentCell = stack.peek();
                    flagStack = true;
                }
            } else {
                //если такие вершины есть, то пребираем соседей до посинения
                while (proceccedCells.get(randNeighbour) != 0) {
                    randNeighbour = currentCell.neighbours.elementAt(random.nextInt(currentCell.neighbours.size()));
                }
                currentCell = randNeighbour;//нашли нужную вершину, перешли в нее
                stack.push(currentCell);
                flagStack = false;
            }
        }
    }

    /**
     * -1 - дорожка
     * 0 - потенциальный путь
     * >0 - точно стена
     */

    private void addNeighbours(Cell cell) {
        for (int i = cell.y - 1; i <= cell.y + 1; i++) {
            for (int j = cell.x - 1; j <= cell.x + 1; j++) {
                if (i > 0 && i < sizeY - 1 && j > 0 && j < sizeX - 1) {
                    if((i != cell.y || j != cell.x) && (Math.abs(cell.x - j) == 1 ^ Math.abs(cell.y - i) == 1)) {
                        cell.neighbours.addElement(labyrinth.elementAt(i).elementAt(j));
                    }
                }
            }
        }
    }

    public int getSizeX(){
        return this.sizeX;
    }

    private void printNeighbours(Cell cell){
        System.out.println("Main cell is " + cell.x + " " + cell.y);
        for(Cell i : cell.neighbours){
            System.out.println("\tSlave cell is " + i.x + " " + i.y);
        }

    }

    public class Cell{
        int x;
        int y;
        boolean isWall;
        boolean wasSeen;
        boolean isPath;
        boolean isUsed;
        Vector<Cell> neighbours;
        int g,h,f;
        Cell cameFrom;

        public Cell(int x, int y){
            this.x = x;
            this.y = y;
            wasSeen = false;
            isPath = false;
            neighbours = new Vector<Cell>();
        }

        public Cell(Cell obj){
            this.x = obj.x;
            this.y = obj.y;
            this.isWall = obj.isWall;
            this.wasSeen = obj.wasSeen;
            this.isPath = obj.isPath;
            this.neighbours = new Vector<>();
        }
    }

}
