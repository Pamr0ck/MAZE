package com.numbernull;

import java.util.*;

/**
 * NULL когда нет пути
 */
public class Astar {
    public static Snapshot snap;

    public static ArrayList<Maze.Cell> search(Maze maze, Maze.Cell sourceCell, Maze.Cell goalCell){
        if(sourceCell.isWall || goalCell.isWall)
            return new ArrayList<Maze.Cell>();

        snap = new Snapshot();

        Map<Maze.Cell, Boolean> closedSet = new HashMap<Maze.Cell, Boolean>();
        Comparator<Maze.Cell> comparator = new CellComparator();
        PriorityQueue<Maze.Cell> openSet = new PriorityQueue<Maze.Cell>(10, comparator);
        Vector<Maze.Cell> path = new Vector<Maze.Cell>();
        sourceCell.g = 0;
        sourceCell.h = heuristicCost(sourceCell, goalCell);
        sourceCell.f = sourceCell.g + sourceCell.h;
        openSet.add(sourceCell);
        snap.addState(maze);
        sourceCell.wasSeen = true;
        while(!openSet.isEmpty()){
            Maze.Cell x = openSet.remove();

             if (x == goalCell)
                return reconstructPath(sourceCell, goalCell);

            closedSet.put(x, true);
            for(Maze.Cell i : x.neighbours) {
                if (!i.isWall) {
                    if (closedSet.containsKey(i)) {
                        continue;
                    }
                    int gScore = x.g + 1;
                    boolean gBetter = false;
                    if (!openSet.contains(i)) {
                        openSet.add(i);
                        gBetter = true;
                    }else {
                        if (gScore < i.g) {
                            gBetter = true;
                        }
                    }
                    if(gBetter){
                        i.cameFrom = x;
                        i.wasSeen = true;
                        i.g = gScore;
                        i.h = heuristicCost(i,x);
                        i.f = i.g + i.h;
                    }
                }
            }
            snap.addState(maze);
        }
        return new ArrayList<Maze.Cell>();
    }

    public static ArrayList<Maze.Cell> reconstructPath(Maze.Cell start, Maze.Cell goal){
        ArrayList<Maze.Cell> path = new ArrayList<Maze.Cell>();
        Maze.Cell currentCell = goal;
        while(currentCell != null){
            path.add(currentCell);
            currentCell = currentCell.cameFrom;
        }
        return path;
    }

    private static int heuristicCost(Maze.Cell a, Maze.Cell b){
        return (b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y);
    }

    public static class CellComparator implements Comparator<Maze.Cell>{
        @Override
        public int compare(Maze.Cell o1, Maze.Cell o2){
            return Integer.compare(o2.f, o1.f);
        }
    }


    public static class Snapshot{
        public ArrayList<Maze> states;

        public Snapshot(){
            states = new ArrayList<>();
        }

        public void addState(final Maze step){
            this.states.add(new Maze(step));
        }

        public Maze getState(Integer iteration){
            return this.states.get(iteration);
        }
    }
}