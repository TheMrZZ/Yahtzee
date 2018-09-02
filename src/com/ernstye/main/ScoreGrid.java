package com.ernstye.main;

import java.util.Arrays;
import java.util.Collections;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;
import static com.ernstye.main.StringUtilities.getLongestStringLenght;
import static com.ernstye.main.StringUtilities.stringFilledWith;

/**
 * Score Grid model object.
 *
 * <p>Contains every row for the player to score</p>
 */
class ScoreGrid
{
    private Integer[] upperSection;

    /**
     * Creates an empty score grid.
     */
    ScoreGrid()
    {
        upperSection = new Integer[DICE_FACES];
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

        // Shows the potential score the player could get for each row
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
     * @param row   the row where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would get
     */
    private int getPotentialScore(int row, Dices dices)
    {
        /*
            If the row is 0, then we're looking for Ones: the number on the dice's face must be 1
            If the row is 1 then we're looking for 2 etc...
         */
        int diceNumber = row + 1;

        /*
         Get the number of dices with the correct face (1 for the "Ones" row, 2 for the "Twos"...
         ex: if the player rolled 4-3-3-2-6, and diceNumber is 3, then correctDices = 2
        */
        int correctDices = Collections.frequency(Arrays.asList(dices.get()), diceNumber);

        /*
        Points are the number of dices with the correct face x the digit
        ex: for the Sixes row, if 3 dices shows 6, the player gets 18 points.
        */
        int score = correctDices * diceNumber;
        return score;
    }

    /**
     * Get the score of a particular row for the player
     *
     * @param row the row to get the score from
     * @return the score of the row
     */
    private int getRowScore(int row)
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

    /**
     * Checks if the score grid is full
     * An full grid means that the game must stops
     *
     * @return True if the grid is full, False if there is still a row with no score
     */
    boolean isFull()
    {
        // If the upper section contains a row without a score, then the grid is not full.
        return !Arrays.asList(upperSection).contains(NO_SCORE);
    }

    /**
     * Displays the score grid and the potential points, in a table shape
     * Table has this format:
     * <p>
     * ---------------------
     * RowName  | X Points | X Potential points
     * ---------------------
     * </p>
     *
     * @param dices if NULL, doesn't display potential points - else, display the potential points the
     *              player could get
     */
    void display(Dices dices)
    {
        // The width of the left column, containing the names of the rows
        final int LEFT_COLUMN_WIDTH = getLongestStringLenght(UPPER_SECTION_ROWS);
        // We then right-pad the names with spaces
        final String LEFT_COLUMN_FORMAT = "%-" + LEFT_COLUMN_WIDTH + "s";

        // The length of the middle column, containing the points the player already scored
        final int MIDDLE_COLUMN_WIDTH = 3;
        final String MIDDLE_COLUMN_FORMAT = "%" + MIDDLE_COLUMN_WIDTH + "s";

        // The right column contains the -optional- potential points
        final String RIGHT_COLUMN_FORMAT = " %3d %s";

        // The total width, equivalent of is (RowName + "|" + Points + "|").length
        final int TOTAL_WIDTH = LEFT_COLUMN_WIDTH + 1 + MIDDLE_COLUMN_WIDTH + 1;
        String ROW_SEPARATOR = stringFilledWith('-', TOTAL_WIDTH);

        System.out.println(ROW_SEPARATOR);
        for (int row = 0; row < upperSection.length; row++)
        {
            int score = getRowScore(row);
            String scoreString = Integer.toString(score);
            if (score == NO_SCORE)
            {
                scoreString = "";
            }

            System.out.printf(LEFT_COLUMN_FORMAT, UPPER_SECTION_ROWS[row]);
            System.out.print("|");

            System.out.printf(MIDDLE_COLUMN_FORMAT, scoreString);
            System.out.print("|");

            // If the player already scored, we don't show the potential points
            if (dices != null && score == NO_SCORE)
            {
                System.out.printf(RIGHT_COLUMN_FORMAT, getPotentialScore(row, dices), "potential points");
            }

            System.out.println();
            System.out.println(ROW_SEPARATOR);
        }

    }
}
