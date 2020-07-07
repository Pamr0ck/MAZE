package com.numbernull;
import java.util.*;

public class DFS {
    public static List<Maze.Cell> search(Maze maze, Maze.Cell sourceCell, Maze.Cell goalCell){

        if(sourceCell.isWall || goalCell.isWall)
            return new ArrayList<Maze.Cell>();

        Stack<Maze.Cell>stack = new Stack<>();
        Maze.Cell curr;
        sourceCell.wasSeen = true;
        stack.push(sourceCell);
        while (!stack.isEmpty()){
            curr = stack.pop();
            if(curr == goalCell)
                return Astar.reconstructPath(sourceCell, goalCell);
            for (int i = 0; i < hasFriends(curr).size();++i) {
                if (!hasFriends(curr).get(i).wasSeen) {
                    hasFriends(curr).get(i).cameFrom = curr;
                    stack.push(hasFriends(curr).get(i));
                    hasFriends(curr).get(i).wasSeen = true;
                }
            }
        }
        return new ArrayList<Maze.Cell>();
    }
    public static List<Maze.Cell> hasFriends(Maze.Cell curr){
        List<Maze.Cell> ans = new ArrayList<Maze.Cell>();
        for (Maze.Cell i: curr.neighbours){
            if (!i.isWall)
                ans.add(i);
        }
        return ans;
    }
}