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
    private String header;
    private int middleColumnWidth;
    private String leftColumnFormat;
    private String rightColumnFormat;
    private String rowSeparator;
    private ScoreGrid scoreGrid;
    private Dices dices;

    /**
     * Creates a table used to display the score grid, with the potential scores given by the dices.
     *
     * @param scoreGrid_ the score grid to display
     * @param dices_     if not null, used to getDices the potential score for each empty row
     */
    Table(ScoreGrid scoreGrid_, Dices dices_)
    {
        this(scoreGrid_, dices_, 3, null, false);
    }

    /**
     * Creates a table used to display the score grid, with the potential scores given by the dices. A potential header
     * can be displayed on top of the middle column, and left & right rows can be hidden.
     *
     * @param scoreGrid_         the score grid to display
     * @param dices_             if not null, used to getDices the potential score for each empty row
     * @param middleColumnWidth_ the width of the middle column
     * @param header_            the header of the middle column
     * @param onlyPointsColumn   if true, left and right columns won't be displayed. Else they will.
     */
    Table(ScoreGrid scoreGrid_, Dices dices_, int middleColumnWidth_, String header_, boolean onlyPointsColumn)
    {
        scoreGrid = scoreGrid_;
        dices = dices_;
        header = header_;

        // The width of the left column, containing the names of the rows
        final int LEFT_COLUMN_WIDTH = getLongestStringLength(SCORE_SHEET_ROWS);

        // We then right-pad the names with spaces
        leftColumnFormat = "%-" + LEFT_COLUMN_WIDTH + "s|";

        // The middle column, containing the points the player already scored, and the potential header
        if (header_ != null)
        {
            middleColumnWidth_ = Math.max(middleColumnWidth_, header_.length());
        }
        middleColumnWidth = middleColumnWidth_;

        // The right column contains the -optional- potential points
        rightColumnFormat = " %3d %s";

        // The total width, equivalent of is (RowName + "|" + Points + "|").length
        totalWidth = middleColumnWidth_ + 1;
        if (!onlyPointsColumn)
        {
            totalWidth += LEFT_COLUMN_WIDTH + 1;
        }

        // If onlyPointsColumn is true, then we don't display the left & right columns at all
        if (onlyPointsColumn)
        {
            leftColumnFormat = "";
            rightColumnFormat = "";
        }

        // Center the header
        if (header != null)
        {
            header = StringUtilities.center(header, middleColumnWidth);
        }

        rowSeparator = stringFilledWith('-', totalWidth);
    }

    /**
     * Displays the score grid and the potential points, in a table shape.
     * Table has this format:
     *
     * <pre>
     * -------------------
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     */
    void display()
    {
        System.out.println(rowSeparator);

        Integer[] scoreSheet = scoreGrid.getScoreSheet();

        // Display the header, for example the player's name
        if (header != null)
        {
            displayRow("", header, "");
        }

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

        // Display the Yahtzee Bonus Points row
        displayYahtzeeBonusRow();

        // Display the actual total score
        System.out.println(rowSeparator);
        displayRow("Total", scoreGrid.getTotalScore(), "");
        System.out.println();
    }

    /**
     * Displays the upper bonus row.
     * If the player doesn't have enough points for the bonus yet, the score will be 0, and a message with the number
     * of points missing will be displayed. If he has enough points, the {@value ScoreGrid#UPPER_BONUS_POINTS} points
     * will be displayed, and the other message will not be displayed.<br>
     * <p>
     * The row is displayed in this format:
     *
     * <pre>
     * Bonus | X score | X points needed before the bonus
     * -------------------
     * </pre>
     */
    private void displayUpperBonusRow()
    {
        int pointsBeforeBonus = scoreGrid.getPointsBeforeUpperBonus();

        // If the player doesn't have the bonus, displays the points needed
        String pointsNeeded = "";
        if (pointsBeforeBonus > 0)
        {
            pointsNeeded = String.format(rightColumnFormat, pointsBeforeBonus, "points needed before the bonus");
        }

        // The upper bonus row has a double separation, to make the difference between the upper & lower section
        displayRow("Bonus", scoreGrid.getUpperBonus(), pointsNeeded);
        System.out.println(rowSeparator);
    }

    /**
     * Display the yahtzee bonus row, on two lines.
     *
     * <p>
     * The row is displayed in this format:
     *
     * <pre>
     * Yahtzee Bonus | X Bonuses       |
     * Bonus Points  | X BonusesPoints |
     * ---------------------------------
     * </pre>
     */
    private void displayYahtzeeBonusRow()
    {
        int yahtzeeBonuses = scoreGrid.getYahtzeeBonuses();
        String bonuses = String.valueOf(yahtzeeBonuses);
        if (yahtzeeBonuses == 0)
        {
            bonuses = "";
        }

        int yahtzeeBonusesPoints = scoreGrid.getYahtzeeBonusPoints();
        String bonusesPoints = String.valueOf(yahtzeeBonusesPoints);

        String bonusPointsExplanation = String.format(rightColumnFormat, YAHTZEE_BONUS_POINTS, "points per bonus");

        displayRowWithoutSeparator("Yahtzee Bonus", bonuses, " Number of bonuses");
        displayRow("Bonus Points", bonusesPoints, bonusPointsExplanation);
    }

    /**
     * Displays a score row - Ones, Twos, Threes etc...
     * The row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
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
     * Displays a single row.
     * Row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
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
     * <pre>
     * rowName |  score  | right
     * -------------------
     * </pre>
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
     * Displays a single row.
     * Row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X right
     * -------------------
     * </pre>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     */
    private void displayRow(String rowName, String score, String right)
    {
        displayRowWithoutSeparator(rowName, score, right);
        System.out.println(rowSeparator);
    }

    /**
     * Displays a single row, without the row separator.
     * Row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X right
     * </pre>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link com.ernstye.main.Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     */
    private void displayRowWithoutSeparator(String rowName, String score, String right)
    {
        System.out.printf(leftColumnFormat, rowName);
        displayMiddleColumn(score);
        System.out.println(right);
    }

    /**
     * Displays the middle column, in a centered way.
     * Centered according to the width given by {@link Table#middleColumnWidth}.
     * <p>
     * Column is displayed in this format (<i> _ represents a white space</i>):
     * <pre>
     * ___ string ___|
     * </pre>
     *
     * @param string the string to display in the middle column
     */
    private void displayMiddleColumn(String string)
    {
        System.out.print(StringUtilities.center(string, middleColumnWidth) + "|");
    }
}
