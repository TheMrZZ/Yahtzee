package com.ernstye.main;

import static com.ernstye.main.Colors.COLOR_PATTERN;
import static com.ernstye.main.Constants.*;
import static com.ernstye.main.StringUtilities.*;

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

    private static String POTENTIAL_COLOR = "RED";
    private static String SCORE_COLOR = "BLUE";

    private boolean needColors;

    /**
     * Creates a table used to display the score grid, with the potential scores given by the dices. A potential header
     * can be displayed on top of the middle column, and left & right rows can be hidden.
     *
     * @param scoreGrid_         the score grid to display
     * @param dices_             if not null, used to getDices the potential score for each empty row
     * @param middleColumnWidth_ the width of the middle column
     * @param header_            the header of the middle column
     * @param onlyPointsColumn   if true, left and right columns won't be displayed. Else they will.
     * @param needColors_         if true, show the colors.
     */
    Table(ScoreGrid scoreGrid_, Dices dices_, int middleColumnWidth_, String header_, boolean onlyPointsColumn, boolean needColors_)
    {
        scoreGrid = scoreGrid_;
        dices = dices_;
        header = header_;
        needColors = needColors_;

        // The width of the left column, containing the names of the rows
        final int LEFT_COLUMN_WIDTH = getLongestStringLength(SCORE_SHEET_ROWS);

        // We then right-pad the names with spaces
        leftColumnFormat = "%-" + LEFT_COLUMN_WIDTH + "s|";

        // The middle column, containing the points the player already scored, and the potential header
        if (header_ != null)
        {
            middleColumnWidth_ = Math.max(middleColumnWidth_, realLength(header_));
            middleColumnWidth_ = Math.max(middleColumnWidth_, 11);
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
     * Return the score grid and the potential points representation, in a table shape.
     * Table has this format:
     *
     * <pre>
     * -------------------
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     *
     * @return the table representation
     */
    @Override
    public String toString()
    {
        String result = "";

        Integer[] scoreSheet = scoreGrid.getScoreSheet();

        // Display the header, for example the player's name
        if (header != null)
        {
            // We don't want the vertical bars for the header
            String fullHeader = getRowWithoutSeparator("", header, "");
            fullHeader = fullHeader.replaceAll("\\|", " ");
            result += fullHeader;
        }

        result += rowSeparator + "\n";

        // Display the upper section
        for (int row = 0; row < UPPER_SECTION_SIZE; row++)
        {
            result += getScoreRow(row);
        }

        // Displays the upper bonus row
        result += getUpperBonusRow();

        // Display the lower section
        for (int row = UPPER_SECTION_SIZE; row < scoreSheet.length; row++)
        {
            result += getScoreRow(row);
        }

        // Display the Yahtzee Bonus Points row
        result += getYahtzeeBonusRow();

        String totalScore = colorize(scoreGrid.getTotalScore(), SCORE_COLOR, "BOLD");

        // Display the actual total score
        result += rowSeparator + "\n";
        result += getRow("Total", totalScore, "");

        if (needColors)
        {
            result = result.replaceAll(COLOR_PATTERN.toString(), "");
        }

        return result;
    }

    /**
     * Get the upper bonus row display.
     * If the player doesn't have enough points for the bonus yet, the score will be 0, and a message with the number
     * of points missing will be displayed. If he has enough points, the {@value ScoreGrid#UPPER_BONUS_POINTS} points
     * will be displayed, and the other message will not be displayed.<br>
     * <p>
     * The row is displayed in this format:
     *
     * <pre>
     * Bonus | X points | X points needed before the bonus
     *       |  needed  |
     * -------------------
     * </pre>
     *
     * @return the upper bonus row representation
     */
    private String getUpperBonusRow()
    {
        int pointsBeforeBonus = scoreGrid.getPointsBeforeUpperBonus();

        // If the player doesn't have the bonus, displays the points needed
        String points;
        String needed = "";
        if (pointsBeforeBonus > 0)
        {
            points = pointsBeforeBonus + " points";
            needed = "needed";
        }
        else
        {
            points = colorize(ScoreGrid.UPPER_BONUS_POINTS, SCORE_COLOR, null);
        }

        String result = "";
        // The upper bonus row has a double separation, to make the difference between the upper & lower section
        result += getRowWithoutSeparator("Bonus", points, "");


        result += getRow("", needed, "");
        result += rowSeparator + "\n";
        return result;
    }

    /**
     * Get the yahtzee bonus row display, on two lines.
     *
     * <p>
     * The row is displayed in this format:
     *
     * <pre>
     * Yahtzee Bonus | X Bonuses       |
     * Bonus Points  | X BonusesPoints |
     * ---------------------------------
     * </pre>
     *
     * @return the yahtzee bonus row representation
     */
    private String getYahtzeeBonusRow()
    {
        int yahtzeeBonuses = scoreGrid.getYahtzeeBonuses();
        String bonuses = String.valueOf(yahtzeeBonuses);
        if (yahtzeeBonuses == 0)
        {
            bonuses = "";
        }

        int yahtzeeBonusesPoints = scoreGrid.getYahtzeeBonusPoints();
        String bonusesPoints = String.valueOf(yahtzeeBonusesPoints);

        String result = "";
        result += getRowWithoutSeparator("Yahtzee Bonus", bonuses, "");
        result += getRow("Bonus Points", bonusesPoints, "");
        return result;
    }

    /**
     * Get a score row display - Ones, Twos, Threes etc...
     * The row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     *
     * @param row the number of the row to display
     * @return the score row representation
     */
    private String getScoreRow(int row)
    {
        String rowName = SCORE_SHEET_ROWS[row];
        int score = scoreGrid.getRowScore(row);

        int potentialScore = NO_SCORE;

        // If the player didn't score in this row, getDices the score he could have
        if (dices != null && score == NO_SCORE)
        {
            potentialScore = scoreGrid.getPotentialScore(row, dices);
        }

        return getSingleRow(rowName, score, potentialScore);
    }

    /**
     * Get a single row display.
     * Row is displayed in this format:
     *
     * <pre>
     * rowName | X score | X potential Score
     * -------------------
     * </pre>
     *
     * @param rowName        the name of the current row
     * @param score          the score to display - if {@link Constants#NO_SCORE} is given,
     *                       nothing will be displayed.
     * @param potentialScore the potential score given by the current {@code dices} - if
     *                       {@link Constants#NO_SCORE} is given, nothing is shown
     * @return the single row representation.
     */
    private String getSingleRow(String rowName, int score, int potentialScore)
    {
        // If the score is NO_SCORE, don't show anything
        String scoreString = "";
        if (score != NO_SCORE)
        {
            scoreString = colorize(score, SCORE_COLOR, null);
        }

        // If the player already scored, we don't show the potential points
        if (potentialScore != NO_SCORE)
        {
            scoreString = colorize(potentialScore, POTENTIAL_COLOR, null);
        }

        return getRow(rowName, scoreString, "");
    }

    /**
     * Convenience wrapper for {@link Table#getRow(String, String, String)}
     * Takes an {@code int} as a score instead of a String.
     * Displays a single row, in this format:
     *
     * <pre>
     * rowName |  score  | right
     * -------------------
     * </pre>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     * @return the single row representation
     * @see Table#getRow(String, String, String)
     */
    private String getRow(String rowName, int score, String right)
    {
        return getRow(rowName, String.valueOf(score), right);
    }

    /**
     * Returns a single row display.
     * Display in this format:
     *
     * <pre>
     * rowName | X score | X right
     * -------------------
     * </pre>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     * @return the single row representation
     */
    private String getRow(String rowName, String score, String right)
    {
        String result = getRowWithoutSeparator(rowName, score, right);
        result += rowSeparator + "\n";
        return result;
    }

    /**
     * Return a single row display, without the row separator.
     * Display is in this format:
     *
     * <pre>
     * rowName | X score | X right
     * </pre>
     *
     * @param rowName the name of the current row
     * @param score   the score to display - if {@link Constants#NO_SCORE} is given,
     *                nothing will be displayed.
     * @param right   the string to display at right of the table
     * @return the single row display
     */
    private String getRowWithoutSeparator(String rowName, String score, String right)
    {
        String result = String.format(leftColumnFormat, rowName);
        result += getMiddleColumn(score);
        result += right + "\n";
        return result;
    }

    /**
     * Gte the middle column display, in a centered way.
     * Centered according to the width given by {@link Table#middleColumnWidth}.
     * <p>
     * Column is displayed in this format (<i> _ represents a white space</i>):
     * <pre>
     * ___ string ___|
     * </pre>
     *
     * @param string the string to display in the middle column
     * @return the middle column display
     */
    private String getMiddleColumn(String string)
    {
        return StringUtilities.center(string, middleColumnWidth) + "|";
    }
}
