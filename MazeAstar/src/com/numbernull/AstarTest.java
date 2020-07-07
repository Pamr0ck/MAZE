package com.numbernull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class AstarTest {
    Maze Big, Mid, Small;
    List<Maze.Cell> A;
    List<Maze.Cell> D;

    @Test
    public void searchBig() {
        Random rnd = new Random();
        int sizeB = rnd.nextInt(10)+70;
        Big = new Maze(sizeB, sizeB);
        for (int j = 0; j < 2; j++) {
            int StartX = rnd.nextInt(Big.getSizeX()-3)+1,
                StartY = rnd.nextInt(Big.getSizeX()-3)+1,
                GoalX = rnd.nextInt(Big.getSizeX()-3)+1,
                GoalY = rnd.nextInt(Big.getSizeX()-3)+1;

            A = Astar.search(Big, Big.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Big, Big.labyrinth.elementAt(StartX).elementAt(StartY),
                    Big.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchMid() {
        Random rnd = new Random();
        int sizeM = rnd.nextInt(10)+30;
        Mid = new Maze(sizeM, sizeM);

        for (int j = 0; j < 2; j++) {
            int StartX = rnd.nextInt(Mid.getSizeX()-3)+1,
                    StartY = rnd.nextInt(Mid.getSizeX()-3)+1,
                    GoalX = rnd.nextInt(Mid.getSizeX()-3)+1,
                    GoalY = rnd.nextInt(Mid.getSizeX()-3)+1;

            A = Astar.search(Mid, Mid.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Mid, Mid.labyrinth.elementAt(StartX).elementAt(StartY),
                    Mid.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
    @Test
    public void searchSmall() {
        Random rnd = new Random();
        int sizeS = rnd.nextInt(10)+4;
        Small = new Maze(sizeS, sizeS);
        for (int j = 0; j < 2; j++) {
            int StartX = rnd.nextInt(Small.getSizeX()-3)+1,
                    StartY = rnd.nextInt(Small.getSizeX()-3)+1,
                    GoalX = rnd.nextInt(Small.getSizeX()-3)+1,
                    GoalY = rnd.nextInt(Small.getSizeX()-3)+1;

            A = Astar.search(Small, Small.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small.labyrinth.elementAt(GoalX).elementAt(GoalY));
            D = DFS.search(Small, Small.labyrinth.elementAt(StartX).elementAt(StartY),
                    Small.labyrinth.elementAt(GoalX).elementAt(GoalY));
            assertEquals(A,D);

        }
    }
}