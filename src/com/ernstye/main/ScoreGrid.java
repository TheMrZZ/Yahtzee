package com.ernstye.main;

import java.util.Arrays;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Score Grid model object.
 *
 * <p>Contains every row for the player to score</p>
 */
class ScoreGrid
{
    // The rows where the points are stored
    private Integer[] scoreSheet;
    private int yahtzeeBonuses;
    private Scorer scorer;

    static final int UPPER_BONUS_POINTS = 35;
    static final int UPPER_SECTION_MINIMUM = 63;

    /**
     * Creates an empty score grid.
     */
    ScoreGrid()
    {
        scoreSheet = new Integer[SCORE_SHEET_ROWS.length];
        Arrays.fill(scoreSheet, NO_SCORE);
        yahtzeeBonuses = 0;
        scorer = new Scorer();
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
        displayFreeRows();

        // If applicable, give the automatic Yahtzee bonus.
        scorePossibleYahtzeeBonus(dices);

        int row = askFreeRow();
        setScore(row, dices);
    }

    /**
     * If possible, will score a Yahtzee Bonus. A Yahtzee Bonus is a bonus you get when the player already scored a
     * Yahtzee, and got the {@value Constants#YAHTZEE_POINTS} points.
     * If he "wasted" his Yahtzee by scoring a 0 into it, the bonus is not applicable.<br>
     * If the bonus is applicable, the player will get an additional {@value Constants#}
     *
     * @param dices the dices the player currently have
     */
    void scorePossibleYahtzeeBonus(Dices dices)
    {
        scorer.setDices(dices);
        // If the player has a Yahtzee, and already scored successfully in the Yahtzee row, give him the bonus
        if (scorer.getYahtzeeScore() > 0 && getRowScore(YAHTZEE_ROW + UPPER_SECTION_SIZE) > 0)
        {
            yahtzeeBonuses++;
        }
    }

    /**
     * Ask a "free" row to the user.
     * Formally, ask for a row having the {@link Constants#NO_SCORE} value.
     *
     * @return a free row index entered by the user.
     */
    private int askFreeRow()
    {
        int row;
        do
        {
            row = askNumber(1, scoreSheet.length + 1) - 1;

            // If the player tries to score in a already taken row
            if (getRowScore(row) != NO_SCORE)
            {
                System.out.println("This row is already taken.");
            }
        } while (getRowScore(row) != NO_SCORE);

        return row;
    }

    /**
     * Get the points a player would score, if he choose an upper section row with his dices.
     *
     * @param row   the row of the upper section where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would get
     */
    int getUpperPotentialScore(int row, Dices dices)
    {
        scorer.setDices(dices);
        return scorer.getUpperRowScore(row);
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
        scorer.setDices(dices);
        switch (row)
        {
            case THREE_OF_A_KIND_ROW:
                return scorer.getThreeOfAKindScore();
            case FOUR_OF_A_KIND_ROW:
                return scorer.getFourOfAKindScore();
            case FULL_HOUSE_ROW:
                return scorer.getFullHouseScore();
            case SMALL_STRAIGHT_ROW:
                return scorer.getSmallStraightScore();
            case LARGE_STRAIGHT_ROW:
                return scorer.getLargeStraightScore();
            case YAHTZEE_ROW:
                return scorer.getYahtzeeScore();
            case CHANCE_ROW:
                return scorer.getChanceScore();
        }

        // In case of incorrect row...
        return 0;
    }

    /**
     * Get the points a player would score, if he choose a particular row with his dices.
     *
     * @param row   the row where the player could score
     * @param dices the dices the player rolled
     * @return the potential points he would get
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
     * @param row the row to get the score from
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
        total += getYahtzeeBonusPoints();
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
     * Get the number of points needed to get the upper bonus.
     *
     * @return the number of points needed to get the upper bonus, 0 if the player already has the bonus
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
     *              player could get
     */
    void display(Dices dices)
    {
        Table table = new Table(this, dices);
        table.display();
    }

    /**
     * Display the "free" rows, in which the player didn't score.
     * Formally, displays the rows having the {@link Constants#NO_SCORE} value.
     */
    private void displayFreeRows()
    {
        for (int i = 0; i < scoreSheet.length; i++)
        {
            if (scoreSheet[i] == NO_SCORE)
            {
                System.out.println((i + 1) + ") " + SCORE_SHEET_ROWS[i]);
            }
        }
    }

    /**
     * Get the {@link ScoreGrid#yahtzeeBonuses} value.
     *
     * @return the yahtzeeBonuses value
     */
    int getYahtzeeBonuses()
    {
        return yahtzeeBonuses;
    }

    /**
     * Get the Yahtzee Bonuses score. Each Yahtzee bonus give {@value Constants#YAHTZEE_BONUS_POINTS} points.
     *
     * @return the current points given by the yahtzee bonuses.
     */
    int getYahtzeeBonusPoints()
    {
        return yahtzeeBonuses * YAHTZEE_BONUS_POINTS;
    }
}
