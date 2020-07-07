package com.numbernull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class AstarTest {
    Maze Big1, Mid1, Small1 ,Big2, Mid2, Small2;
    List<Maze.Cell> A;
    List<Maze.Cell> D;

    @Test
    public void searchBig() {
        Random rnd = new Random();
        int sizeB = rnd.nextInt(10)+70;
        for (int j = 0; j < 2; j++) {
            Big1 = new Maze(sizeB);
            Big2 = Big1;
            int StartX = rnd.nextInt(Big1.getSizeX()-3)+1,
                StartY = rnd.nextInt(Big1.getSizeX()-3)+1,
                GoalX = rnd.nextInt(Big1.getSizeX()-3)+1,
                GoalY = rnd.nextInt(Big1.getSizeX()-3)+1;

            A = Astar.search(Big1, Big1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Big2, Big2.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big2.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchMid() {
        Random rnd = new Random();
        int sizeM = rnd.nextInt(10)+30;
        for (int j = 0; j < 2; j++) {
            Mid1 = new Maze(sizeM);
            Mid2 = Mid1;
            int StartX = rnd.nextInt(Mid1.getSizeX()-3)+1,
                    StartY = rnd.nextInt(Mid1.getSizeX()-3)+1,
                    GoalX = rnd.nextInt(Mid1.getSizeX()-3)+1,
                    GoalY = rnd.nextInt(Mid1.getSizeX()-3)+1;

            A = Astar.search(Mid1, Mid1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Mid2, Mid2.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid2.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchSmall() {
        Random rnd = new Random();
        int sizeS = rnd.nextInt(10)+4;
        for (int j = 0; j < 2; j++) {
            Small1 = new Maze(sizeS);
            Small2 = Small1;
            int StartX = rnd.nextInt(Small1.getSizeX()-3)+1,
                    StartY = rnd.nextInt(Small1.getSizeX()-3)+1,
                    GoalX = rnd.nextInt(Small1.getSizeX()-3)+1,
                    GoalY = rnd.nextInt(Small1.getSizeX()-3)+1;

            A = Astar.search(Small1, Small1.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small1.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Small2, Small2.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small2.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(D,A);

        }
    }
}