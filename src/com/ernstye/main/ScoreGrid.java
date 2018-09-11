package com.ernstye.main;

import java.util.Arrays;
import java.util.List;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Score Grid model object.
 *
 * <p>Contains every row (upper section only) for the player to score</p>
 */
class ScoreGrid
{
    // The rows where the points are stored
    private Integer[] scoreSheet;
    private int YahtzeeBonuses = 0;

    static final int UPPER_BONUS_POINTS = 35;
    static final int UPPER_SECTION_MINIMUM = 63;

    /**
     * Creates an empty score grid.
     */
    ScoreGrid()
    {
        scoreSheet = new Integer[SCORE_SHEET_ROWS.length];
        Arrays.fill(scoreSheet, NO_SCORE);
    }

    /**
     * {@link ScoreGrid#scoreSheet} getter.
     *
     * @return the score sheet
     */
    Integer[] getScoreSheet()
    {
        return scoreSheet;
    }

    /**
     * Allows the player to choose a row to score.
     *
     * @param dices the dices rolled by the player
     */
    void score(Dices dices)
    {
        System.out.println("Score points in which row?");

        // Shows the potential score the player could getDices for each row
        for (int i = 0; i < scoreSheet.length; i++)
        {
            if (scoreSheet[i] == NO_SCORE)
            {
                System.out.println((i + 1) + ") " + SCORE_SHEET_ROWS[i]);
            }
        }

        int row;
        do
        {
            row = askNumber(1, scoreSheet.length + 1) - 1;
            if (getRowScore(row) != NO_SCORE)
            {
                System.out.println("This row is already taken.");
            }
        } while (getRowScore(row) != NO_SCORE);
        setScore(row, dices);
    }

    /**
     * Returns the potential score of a player, in the Three Of A Kind row (at least 3 times the same dice value).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Three Of A Kind
     */
    private int getThreeOfAKindScore(Dices dices)
    {
        // Get the sorted list of dice occurrences.
        List<Integer> diceOccurrencesSorted = dices.getAllOccurrences(true);

        if (diceOccurrencesSorted.get(0) >= 3)
        {
            return dices.sum();
        }
        return 0;
    }


    /**
     * Returns the potential score of a player, in the Four Of A Kind row (at least 4 times the same dice value).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Four Of A Kind
     */
    private int getFourOfAKindScore(Dices dices)
    {
        // Get the sorted list of dice occurrences.
        List<Integer> diceOccurrencesSorted = dices.getAllOccurrences(true);

        if (diceOccurrencesSorted.get(0) >= 4)
        {
            return dices.sum();
        }
        return 0;
    }


    /**
     * Returns the potential score of a player, in the Full House row
     * (3 dices of same value, and 2 other dices of same value).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Full House
     */
    private int getFullHouseScore(Dices dices)
    {
        // Get the sorted list of dice occurrences.
        List<Integer> diceOccurrencesSorted = dices.getAllOccurrences(true);

        if (diceOccurrencesSorted.get(0) >= 3 && diceOccurrencesSorted.get(1) >= 2)
        {
            return FULL_HOUSE_POINTS;
        }
        return 0;
    }


    /**
     * Returns the potential score of a player, in the Small Straight row
     * (A straight of at least 4 dices).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Full House
     */
    private int getSmallStraightScore(Dices dices)
    {
        if (dices.getLongestStraightSize() >= 4)
        {
            return SMALL_STRAIGHT_POINTS;
        }
        return 0;
    }

    /**
     * Returns the potential score of a player, in the Large Straight row
     * (A straight of at least 5 dices).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Full House
     */
    private int getLargeStraightScore(Dices dices)
    {
        if (dices.getLongestStraightSize() >= 5)
        {
            return LARGE_STRAIGHT_POINTS;
        }
        return 0;
    }

    /**
     * Returns the potential score of a player, in the Yahtzee row (all dices have the same value).
     *
     * @param dices the dices the player have
     * @return the potential score the player would get by scoring a Four Of A Kind
     */
    private int getYahtzeeScore(Dices dices)
    {
        // Get the sorted list of dice occurrences.
        List<Integer> diceOccurrencesSorted = dices.getAllOccurrences(true);

        if (diceOccurrencesSorted.get(0) == NUMBER_OF_DICES)
        {
            return YAHTZEE_POINTS;
        }
        return 0;
    }

    /**
     * Get the points a player would score, if he choose an upper section row with his dices.
     *
     * @param row   the row of the upper section where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would getDices
     */
    int getUpperPotentialScore(int row, Dices dices)
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
        int correctDices = dices.getOccurrencesOf(diceNumber);

        /*
         Points are the number of dices with the correct face x the digit
         ex: for the Sixes row, if three dices shows 6, the player gets 18 points.
        */
        return correctDices * diceNumber;
    }

    /**
     * Get the score a player would get by scoring into a lower's section row.
     *
     * @param row   the row from the lower section. Potential values are
     *              {@link Constants#THREE_OF_A_KIND_ROW}, {@link Constants#FOUR_OF_A_KIND_ROW},
     *              {@link Constants#FULL_HOUSE_ROW}, {@link Constants#SMALL_STRAIGHT_ROW},
     *              {@link Constants#LARGE_STRAIGHT_ROW}, {@link Constants#YAHTZEE_ROW}
     * @param dices the dices the player has
     * @return the potential points the player would get by scoring in the specified {@code row}.
     * 0 if the row is incorrect.
     */
    int getLowerPotentialScore(int row, Dices dices)
    {
        switch (row)
        {
            case THREE_OF_A_KIND_ROW:
                return getThreeOfAKindScore(dices);
            case FOUR_OF_A_KIND_ROW:
                return getFourOfAKindScore(dices);
            case FULL_HOUSE_ROW:
                return getFullHouseScore(dices);
            case SMALL_STRAIGHT_ROW:
                return getSmallStraightScore(dices);
            case LARGE_STRAIGHT_ROW:
                return getLargeStraightScore(dices);
            case YAHTZEE_ROW:
                return getYahtzeeScore(dices);
            case CHANCE_ROW:
                return dices.sum();
        }

        // In case of incorrect row...
        return 0;
    }

    /**
     * Get the points a player would score, if he choose a particular row with his dices.
     *
     * @param row   the row where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would getDices
     */
    int getPotentialScore(int row, Dices dices)
    {
        if (row < UPPER_SECTION_SIZE)
        {
            return getUpperPotentialScore(row, dices);
        }

        // The row of the lower section is the actual row, minus the number of rows in the upper section
        int lowerSectionRow = row - UPPER_SECTION_SIZE;

        return getLowerPotentialScore(lowerSectionRow, dices);
    }

    /**
     * Get the score of a particular row for the player.
     *
     * @param row the row to getDices the score from
     * @return the score of the row
     */
    int getRowScore(int row)
    {
        return scoreSheet[row];
    }

    /**
     * Set the score given by {@code dices} in the given {@code row}.
     *
     * @param row   the row to write the score in
     * @param dices the dices rolled by the player
     */
    void setScore(int row, Dices dices)
    {
        int score = getPotentialScore(row, dices);
        scoreSheet[row] = score;
    }

    /**
     * Checks if the score grid is full.
     * A full grid means that the game must stops.
     *
     * @return True if the grid is full, False if there is still a row with no score
     */
    boolean isFull()
    {
        // If the upper section contains a row without a score, then the grid is not full.
        return !Arrays.asList(scoreSheet).contains(NO_SCORE);
    }

    /**
     * Get the total points of the grid.
     *
     * @return the upper section grid plus the bonus if the player has one
     */
    int getTotalScore()
    {
        int total = 0;
        total += getUpperSectionScore();
        total += getUpperBonus();
        total += getLowerSectionScore();
        return total;
    }

    /**
     * Get the sum of the points of each row in the upper section.
     *
     * @return the upper section score
     */
    private int getUpperSectionScore()
    {
        int total = 0;
        for (int i = 0; i < UPPER_SECTION_SIZE; i++)
        {
            int score = scoreSheet[i];
            // If the player didn't score, we don't add the current row to the total points
            if (score != NO_SCORE)
            {
                total += score;
            }
        }

        return total;
    }

    /**
     * Get the sum of the points of each row in the upper section.
     *
     * @return the upper section score
     */
    private int getLowerSectionScore()
    {
        int total = 0;
        for (int i = UPPER_SECTION_SIZE; i < SCORE_SHEET_ROWS.length; i++)
        {
            int score = scoreSheet[i];
            // If the player didn't score, we don't add the current row to the total points
            if (score != NO_SCORE)
            {
                total += score;
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
     * Get the number of points needed to getDices the upper bonus.
     *
     * @return the number of points needed to getDices the upper bonus, 0 if the player already has the bonus
     */
    int getPointsBeforeUpperBonus()
    {
        return Math.max(0, UPPER_SECTION_MINIMUM - getUpperSectionScore());
    }

    /**
     * Displays the score grid and the potential points, in a table shape.
     * Table has this format:
     *
     * <pre>
     * ---------------------
     * RowName  | X Points | X Potential points
     * ---------------------
     * </pre>
     *
     * @param dices if NULL, doesn't display potential points - else, display the potential points the
     *              player could getDices
     */
    void display(Dices dices)
    {
        Table table = new Table(this, dices);
        table.display();
    }
}
