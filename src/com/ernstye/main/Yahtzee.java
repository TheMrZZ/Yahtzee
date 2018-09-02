package com.ernstye.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;
import static com.ernstye.main.Dices.*;

public class Yahtzee
{
    /**
     * A working Yahtzee implementation.
     * <p>
     * TODO:
     * - Implement a single-player working version of the dices
     * - Implement the upper section of the points
     * </p>
     */
    public static void main(String[] args)
    {
        int continue_ = 1;
        int i = 0;
        System.out.println("== YAHTZEE ==");
        Dices dices = new Dices();
        dices.play();
        startGame();
    }

    private static void startGame()
    {
        System.out.println("== YAHTZEE ==");
        Dices dices = new Dices();
        ScoreGrid scoreGrid = new ScoreGrid();

        while (!scoreGrid.isFull())
        {
            dices.roll();
            dices.display();

            System.out.println();

            scoreGrid.display(dices);
            scoreGrid.score(dices);
        }

        System.out.println("\n\n=== FINAL RESULTS ===\n\n");
        scoreGrid.display(null);
        System.out.println("You scored a total of " + scoreGrid.getTotalScore() + " points!");
    }
}
