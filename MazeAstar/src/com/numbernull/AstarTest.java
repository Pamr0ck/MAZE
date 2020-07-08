package com.numbernull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstarTest {
    Maze Big1, Mid1, Small1 ,Big2, Mid2, Small2;
    List<Maze.Cell> A;
    List<Maze.Cell> D;
    int StartX;
    int StartY;
    int GoalX;
    int GoalY;
    private int[] genCoords(Maze m){
        Random rnd = new Random();
        int[] ans = new int[4];
        StartX = rnd.nextInt(m.sizeX-1);
        StartY = rnd.nextInt(m.sizeX-1);
        GoalX = rnd.nextInt(m.sizeX-1);
        GoalY = rnd.nextInt(m.sizeX-1);
        while (m.labyrinth.elementAt(StartY).elementAt(StartX).isWall){
            StartX = rnd.nextInt(m.sizeX-1);
            StartY = rnd.nextInt(m.sizeX-1);
        }
        while (m.labyrinth.elementAt(GoalY).elementAt(GoalX).isWall){
            GoalX = rnd.nextInt(m.sizeX-1);
            GoalY = rnd.nextInt(m.sizeX-1);
        }
        ans[0] = StartX;
        ans[1] = StartY;
        ans[2] = GoalX;
        ans[3] = GoalY;
        return ans;
    }

    @Test
    public void searchBig() {
        Random rnd = new Random();
        int sizeB = rnd.nextInt(10)+70;
        for (int j = 0; j < 2; j++) {
            Big1 = new Maze(sizeB);
            int[] tmp = genCoords(Big1);
            StartX = tmp[0];
            StartY = tmp[1];
            GoalX = tmp[2];
            GoalY = tmp[3];

            A = Astar.search(Big1, Big1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            Big1.clear();
            D = DFS.search(Big1, Big1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchMid() {
        Random rnd = new Random();
        int sizeM = rnd.nextInt(10)+30;
        for (int j = 0; j < 2; j++) {
            Mid1 = new Maze(sizeM);
            int[] tmp = genCoords(Mid1);
            StartX = tmp[0];
            StartY = tmp[1];
            GoalX = tmp[2];
            GoalY = tmp[3];
            A = Astar.search(Mid1, Mid1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            Mid1.clear();
            D = DFS.search(Mid1, Mid1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchSmall() {
        Random rnd = new Random();
        int sizeS = rnd.nextInt(10)+6;
        for (int j = 0; j < 2; j++) {
            Small1 = new Maze(sizeS);
            int[] tmp = genCoords(Small1);
            StartX = tmp[0];
            StartY = tmp[1];
            GoalX = tmp[2];
            GoalY = tmp[3];
            A = Astar.search(Small1, Small1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            Small1.clear();
            D = DFS.search(Small1, Small1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(D,A);

        }
    }
}