package com.ernstye.main;

import java.util.Scanner;

public class Player
{
    private String name;
    private Dices dices;
    private ScoreGrid scoreGrid;

    Player()
    {
        dices = new Dices();
        scoreGrid = new ScoreGrid();
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your name:");
        name = scanner.nextLine();

    }

    Dices getDices()
    {
        return dices;
    }

    String getName()
    {
        return name;
    }

    ScoreGrid getScoreGrid()
    {
        return scoreGrid;
    }

    void play()
    {

        dices.play(scoreGrid);
        System.out.println();
        scoreGrid.score(dices);
        if (scoreGrid.isFull())
        {
            System.out.println("\n\n=== FINAL RESULTS PLAYER " + name + " ===\n\n");
            scoreGrid.display(null);
            System.out.println("You scored a total of " + scoreGrid.getTotalScore() + " points!");
        }
    }

    int totalScore()
    {
        return scoreGrid.getTotalScore();
    }

}
