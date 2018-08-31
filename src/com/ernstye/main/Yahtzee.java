package com.ernstye.main;

import java.util.Arrays;

import static com.ernstye.main.Constants.*;

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
