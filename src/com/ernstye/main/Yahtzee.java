package com.ernstye.main;

import java.util.Arrays;

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
        System.out.println("== YAHTZEE ==");

        int[] upperSection = createUpperSection();
        Dices dices=new Dices();
        dices.roll();
        dices.display();
        for (int i=0; i<2;i++)
        {
            System.out.println("\nTURN "+(i+2));
            dices.nextTurn();
            dices.display();
        }


    }

    public static void displayDices(int dices[])
    {
        for (int i=0; i<6;i++)
        {
            System.out.println(i+") "+ dices[i]);
        }
    }

    // Create the empty upper section: 6 rows for each possible scores
    public static int[] createUpperSection()
    {
        int[] upperSection = new int[NUMBER_OF_DICES];
        Arrays.fill(upperSection, NO_SCORE);
        return upperSection;
    }


    public static void scorePoints(int[] upperSection, int[] dices)
    {
        System.out.println("Score points in which section?");
        for (int i = 0; i < upperSection.length; i++)
        {
            System.out.println(i+1 + ") " + UPPER_SECTION_ROWS[i]);
        }

    }

}
