package com.ernstye.main;

import java.util.Arrays;
import java.util.Collections;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Score Grid model object.
 *
 * <p>Contains every row (upper section only) for the player to score</p>
 */
class ScoreGrid
{
    private Integer[] upperSection;
    final int UPPER_BONUS_POINTS = 35;
    final int UPPER_SECTION_MINIMUM = 63;

    /**
     * Creates an empty score grid.
     */
    ScoreGrid()
    {
        upperSection = new Integer[DICE_FACES];
        Arrays.fill(upperSection, NO_SCORE);
    }

    Integer[] getUpperSection()
    {
        return upperSection.clone();
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
                System.out.println((i + 1) + ") " + UPPER_SECTION_ROWS[i]);
            }
        }

        int row;
        do
        {
            row = askNumber(1, upperSection.length + 1) - 1;
            if (getRowScore(row) != NO_SCORE)
            {
                System.out.println("This row is already taken.");
            }
        } while (getRowScore(row) != NO_SCORE);
        addScore(row, dices);
    }

    /**
     * Get the points a player would score, if he choose a particular row with his dices
     *
     * @param row   the row where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would get
     */
    int getPotentialScore(int row, Dices dices)
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
     * Get the total points of the grid
     *
     * @return the upper section grid plus the bonus if the player has one
     */
    int getTotalScore()
    {
        int total = 0;
        total += getUpperSectionScore();
        total += getUpperBonus();
        return total;
    }

    /**
     * Get the sum of the points of each row in the upper section
     *
     * @return the upper section score
     */
    int getUpperSectionScore()
    {
        int total = 0;
        for (int i = 0; i < upperSection.length; i++)
        {
            // If the player didn't score, we don't add the current row to the total points
            if (upperSection[i] != NO_SCORE)
            {
                total += upperSection[i];
            }
        }

        return total;
    }

    /**
     * If the player has 63 points or more in the upper section, he gets a bonus of 35 points.
     *
     * @return 0 if the player has less than 63 points in the upper section, 35 else.
     */
    int getUpperBonus()
    {
        if (getUpperSectionScore() >= UPPER_SECTION_MINIMUM)
        {
            return UPPER_BONUS_POINTS;
        }

        return 0;
    }

    /**
     * Get the number of points needed to get the upper bonus
     *
     * @return the number of points needed to get the upper bonus, 0 if the player already has the bonus
     */
    int getPointsBeforeUpperBonus()
    {
        return Math.max(0, UPPER_SECTION_MINIMUM - getUpperBonus());
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
        Table table = new Table(this, dices);
        table.display();
    }
}
