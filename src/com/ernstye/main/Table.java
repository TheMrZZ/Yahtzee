package com.ernstye.main;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.StringUtilities.getLongestStringLength;
import static com.ernstye.main.StringUtilities.stringFilledWith;

/**
 * Class used for display purposes.
 * Will show the grid as a table with the names and the points of each row,
 * and the potential points given by the current dices.
 */
class Table
{
    private int totalWidth;
    private String leftColumnFormat;
    private String middleColumnFormat;
    private String rightColumnFormat;
    private String rowSeparator;
    private ScoreGrid scoreGrid;
    private Dices dices;

    /**
     * Creates a table used to display the score grid, with the potential scores given by the dices
     *
     * @param scoreGrid_ the score grid to display
     * @param dices_     if not null, used to getDices the potential score for each empty row
     */
    Table(ScoreGrid scoreGrid_, Dices dices_)
    {
        scoreGrid = scoreGrid_;
        dices = dices_;

        // The width of the left column, containing the names of the rows
        final int LEFT_COLUMN_WIDTH = getLongestStringLength(SCORE_SHEET_ROWS);

        // We then right-pad the names with spaces
        leftColumnFormat = "%-" + LEFT_COLUMN_WIDTH + "s";

        // The length of the middle column, containing the points the player already scored
        final int MIDDLE_COLUMN_WIDTH = 3;
        middleColumnFormat = "%" + MIDDLE_COLUMN_WIDTH + "s";

        // The right column contains the -optional- potential points
        rightColumnFormat = " %3d %s";

        // The total width, equivalent of is (RowName + "|" + Points + "|").length
        totalWidth = LEFT_COLUMN_WIDTH + 1 + MIDDLE_COLUMN_WIDTH + 1;

        rowSeparator = stringFilledWith('-', totalWidth);
    }

    /**
     * Displays the score grid and the potential points, in a table shape
     * Table has this format:
     * <p>
     * <pre>
     * -------------------
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     * </p>
     */
    void display()
    {
        System.out.println(rowSeparator);

        Integer[] scoreSheet = scoreGrid.getScoreSheet();

        // Display the upper section
        for (int row = 0; row < UPPER_SECTION_SIZE; row++)
        {
            displayScoreRow(row);
        }

        // Displays the upper bonus row
        displayUpperBonusRow();

        // Display the lower section
        for (int row = UPPER_SECTION_SIZE; row < scoreSheet.length; row++)
        {
            displayScoreRow(row);
        }

        // Display the actual total score
        displayRow("Total", String.valueOf(scoreGrid.getTotalScore()), "");
        System.out.println();
    }

    private void displayUpperBonusRow()
    {
        int pointsBeforeBonus = scoreGrid.getPointsBeforeUpperBonus();

        // If the player doesn't have the bonus, displays the points needed
        String pointsNeeded = "";
        if (pointsBeforeBonus > 0)
        {
            pointsNeeded = String.format(rightColumnFormat, pointsBeforeBonus, "points needed before the bonus");
        }

        displayRow("Bonus", String.valueOf(scoreGrid.getUpperBonus()), pointsNeeded);
    }

    /**
     * Displays a score row - Ones, Twos, Threes etc...
     * The row is displayed in this format:
     * <p>
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     * </p>
     *
     * @param row the number of the row to display
     */
    private void displayScoreRow(int row)
    {
        String rowName = SCORE_SHEET_ROWS[row];
        int score = scoreGrid.getRowScore(row);

        int potentialScore = NO_SCORE;

        // If the player didn't score in this row, getDices the score he could have
        if (dices != null && score == NO_SCORE)
        {
            potentialScore = scoreGrid.getPotentialScore(row, dices);
        }

        displaySingleRow(rowName, score, potentialScore);
    }

    /**
     * Displays a single row, in this format:
     * <p>
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     * </p>
     *
     * @param rowName        the name of the current row
     * @param score          the score to display - if {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                       nothing will be displayed.
     * @param potentialScore the potential score given by the current {@code dices} - if
     *                       {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                       nothing will be displayed.
     */
    private void displaySingleRow(String rowName, int score, int potentialScore)
    {
        // If the score is NO_SCORE, don't show anything
        String scoreString = Integer.toString(score);
        if (score == NO_SCORE)
        {
            scoreString = "";
        }

        String potentialScoreString = "";

        // If the player already scored, we don't show the potential points
        if (potentialScore != NO_SCORE)
        {
            potentialScoreString = String.format(rightColumnFormat, potentialScore, "potential points");
        }

        displayRow(rowName, scoreString, potentialScoreString);
    }

    /**
     * Convenience wrapper for {@link com.ernstye.main.Table#displayRow(String, String, String)}
     * Takes an {@code int} as a score instead of a String.
     * Displays a single row, in this format:
     *
     * <p>
     * <pre>
     * rowName |  score  | right
     * -------------------
     * </pre>
     * </p>
     *
     *
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     * @see com.ernstye.main.Table#displayRow(String, String, String)
     */
    private void displayRow(String rowName, int score, String right)
    {
        displayRow(rowName, String.valueOf(score), right);
    }

    /**
     * Displays a single row, in this format:
     * <p>
     * <pre>
     * rowName | X score | X right
     * -------------------
     * </pre>
     * </p>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     */
    private void displayRow(String rowName, String score, String right)
    {
        System.out.printf(leftColumnFormat, rowName);
        System.out.print("|");

        System.out.printf(middleColumnFormat, score);
        System.out.print("|");

        System.out.println(right);
        System.out.println(rowSeparator);
    }
}
