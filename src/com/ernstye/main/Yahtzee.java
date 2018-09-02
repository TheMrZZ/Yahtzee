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
        dices.start();

        /*ScoreGrid scoreGrid = new ScoreGrid();
        scoreGrid.display(dices);
        scoreGrid.score(dices);
        dices.roll();
        dices.display();
        scoreGrid.display(dices);*/
    }
}
