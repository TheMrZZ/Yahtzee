package com.ernstye.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Score Grid model object.
 *
 * <p>Contains every row for the player to score</p>
 */
class ScoreGrid
{
    private int[] upperSection;

    /**
     * Creates an empty score grid.
     */
    ScoreGrid()
    {
        upperSection = new int[DICE_FACES];
        Arrays.fill(upperSection, NO_SCORE);
    }

    /**
     * Allows the player to choose a row to score.
     *
     * @param dices the dices rolled by the player
     */
    void score(Dices dices)
    {
        System.out.println("Score points in which row?");
        for (int i = 0; i < upperSection.length; i++)
        {
            if (upperSection[i] == NO_SCORE)
            {
                System.out.print((i + 1) + ") " + UPPER_SECTION_ROWS[i] + "\t");
                System.out.println(getPotentialScore(i, dices) + " potential points");
            }
        }

        int row = askNumber(1, upperSection.length + 1, "Choose a section") - 1;
        addScore(row, dices);
    }

    /**
     * Get the points a player would score, if he choose a particular row with his dices
     *
     * @param row the row where the player could score
     * @param dices the dices the player rolled
     *
     * @return the potential points he would get
     */
    private int getPotentialScore(int row, Dices dices)
    {
        // Get the number of dices with the correct face (1 for the "Ones" row, 2 for the "Twos"...
        int correctDices = Collections.frequency(Arrays.asList(dices.get()), row + 1);

        // Points are the number of dices with the correct face x the digit
        // ex: for the Sixes row, if 3 dices shows 6, the player gets 18 points.
        int score = correctDices * (row + 1);
        return score;
    }

    /**
     * Get the score of a particular row for the player
     *
     * @param row the row to get the score from
     * @return the score of the row
     */
    int getRowScore(int row)
    {
        return upperSection[row];
    }

    /**
     * Mark the score of dices in a row
     *
     * @param row   the row to write the score in
     * @param dices the dices rolled by the player
     */
    private void addScore(int row, Dices dices)
    {
        upperSection[row] = getPotentialScore(row, dices);
    }
}
