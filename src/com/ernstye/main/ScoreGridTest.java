package com.ernstye.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.ernstye.main.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class testing the {@link ScoreGrid} class.
 *
 * <strong>The {@code JUnit5} framework is required for this to work.</strong>
 * <p>
 * Instructions to install JUnit5:
 * <ul>
 * <li><a href="https://www.jetbrains.com/help/idea/configuring-testing-libraries.html">For Intellij IDEA</a></li>
 * <li><a href="https://issues.apache.org/jira/browse/NETBEANS-6">For Netbeans (not fully supported)</a></li>
 * <li><a href="https://www.eclipse.org/community/eclipse_newsletter/2017/october/article5.php">For Eclipse</a></li>
 * <li><a href="https://github.com/junit-team/junit5/">For global installation - not IDE specific</a></li>
 * </ul>
 */
class ScoreGridTest
{
    private ScoreGrid scoreGrid;
    private Dices dices;

    /**
     * Flag the "expected points" as the sum of the dices.<br>
     * <p>
     * Useful for {@link ScoreGridTest#THREE_OF_A_KIND}
     * and {@link ScoreGridTest#FOUR_OF_A_KIND},
     * because their expected results are not constants.
     */
    static private final int SUM_OF_DICES = -1;

    /**
     * Three of a kind presets.
     */
    private static final Integer[][] THREE_OF_A_KIND = {
        {2, 3, 4, 2, 2},
        {6, 2, 6, 1, 6},
        {4, 4, 4, 2, 3},
        {2, 6, 1, 1, 1}
    };

    /**
     * Four of a kind presets. Can get considered as {@link ScoreGridTest#THREE_OF_A_KIND}.
     */
    private static final Integer[][] FOUR_OF_A_KIND = {
        {1, 6, 6, 6, 6},
        {5, 2, 5, 5, 5},
        {4, 4, 6, 4, 4},
        {3, 3, 3, 2, 3},
        {2, 2, 2, 2, 1},
        {1, 1, 4, 1, 1}
    };

    /**
     * Full House presets. Can get considered as {@link ScoreGridTest#THREE_OF_A_KIND}.
     */
    private static final Integer[][] FULL_HOUSES = {
        {1, 2, 1, 1, 2},
        {2, 3, 3, 3, 2},
        {6, 4, 6, 4, 6},
        {3, 3, 3, 5, 5}
    };

    /**
     * Small Straight presets.
     */
    private static final Integer[][] SMALL_STRAIGHTS = {
        {1, 2, 4, 3, 6},
        {3, 1, 5, 6, 4}
    };

    /**
     * Large Straight presets. Can get considered as {@link ScoreGridTest#SMALL_STRAIGHTS}
     */
    private static final Integer[][] LARGE_STRAIGHTS = {
        {1, 2, 5, 4, 3},
        {2, 4, 5, 3, 6}
    };

    /**
     * Yahtzee presets. Can get considered as {@link ScoreGridTest#THREE_OF_A_KIND} and
     * as {@link ScoreGridTest#FOUR_OF_A_KIND}.
     */
    private static final Integer[][] YAHTZEE = {
        {1, 1, 1, 1, 1},
        {2, 2, 2, 2, 2},
        {3, 3, 3, 3, 3},
        {4, 4, 4, 4, 4},
        {5, 5, 5, 5, 5},
        {6, 6, 6, 6, 6}
    };

    /**
     * Start each test with an empty scoreGrid.
     */
    @BeforeEach
    void setUp()
    {
        scoreGrid = new ScoreGrid();
        dices = new Dices();
    }

    /**
     * Assert that one lower row gets the expected results.
     *
     * @param row      the row to test
     * @param actual   the actual score given by the
     *                 {@link ScoreGrid#getLowerPotentialScore(int, Dices)} method
     * @param expected the expected score
     */
    private void assertRowEquals(int row, int actual, int expected)
    {
        String rowName = SCORE_SHEET_ROWS[row];
        String dicesDisplay = Arrays.asList(dices.getDices()).toString();
        String errorMessage = "Got " + actual + " instead of " + expected + " for row=" + rowName + " and dices=" + dicesDisplay;
        assertEquals(expected, actual, errorMessage);
    }

    /**
     * Assert the player has 0 points in every row in the lower section,
     * except for given exceptions rows, and except for the Chance row.
     *
     * @param exceptionRows the rows which should NOT have a score of 0
     */
    private void assertZeroPointsLowerSection(Integer[] exceptionRows)
    {
        List<Integer> exceptions = Arrays.asList(exceptionRows);
        for (int row = THREE_OF_A_KIND_ROW; row <= YAHTZEE_ROW; row++)
        {
            if (!exceptions.contains(row) && row != CHANCE_ROW)
            {
                int actual = scoreGrid.getLowerPotentialScore(row, dices);
                assertRowEquals(row + UPPER_SECTION_SIZE, actual, 0);
            }
        }
    }

    /**
     * Tests if a lower section row gets the correct values for a bunch presets.
     *
     * @param row         the row to test
     * @param expected    the expected score - If {@link ScoreGridTest#SUM_OF_DICES} is given,
     *                    the sum of the dices will be considered as the expected score.
     * @param dicesValues the values of the dices
     * @param nonZeroRows the rows that should NOT have a potential score of 0
     */
    private void testOneLowerSectionRow(int row, int expected, Integer[][] dicesValues, Integer[] nonZeroRows)
    {
        boolean needSumOfDices = expected == SUM_OF_DICES;

        for (Integer[] dicesValue : dicesValues)
        {
            dices.setDices(dicesValue);

            if (needSumOfDices)
            {
                expected = dices.sum();
            }

            int actual = scoreGrid.getLowerPotentialScore(row, dices);
            assertRowEquals(row + UPPER_SECTION_SIZE, actual, expected);
            assertZeroPointsLowerSection(nonZeroRows);
        }
    }

    /**
     * Tests the {@link ScoreGrid#getLowerPotentialScore(int, Dices)}
     * method with preset dices rolls, and with known expected scores.<br>
     * Dices presets are:
     * {@link ScoreGridTest#THREE_OF_A_KIND},
     * {@link ScoreGridTest#FOUR_OF_A_KIND},
     * {@link ScoreGridTest#FULL_HOUSES},
     * {@link ScoreGridTest#SMALL_STRAIGHTS},
     * {@link ScoreGridTest#LARGE_STRAIGHTS},
     * {@link ScoreGridTest#YAHTZEE},
     */
    @Test
    void getLowerPotentialScore()
    {
        // Disable Intellij's IDEA auto formatting for next lines
        // @formatter:off

        /*
        The rows to test. The format is the following:
        0. The row to test
        1. The expected value
        2. The set of values the dices will take (more values means the code tests more configurations)
        3. The rows that should NOT be equal to 0 in the lower section
         */
        Object[][] rowTests = {
            {THREE_OF_A_KIND_ROW,   SUM_OF_DICES,           THREE_OF_A_KIND,    new Integer[]{THREE_OF_A_KIND_ROW}},
            {FOUR_OF_A_KIND_ROW,    SUM_OF_DICES,           FOUR_OF_A_KIND,     new Integer[]{FOUR_OF_A_KIND_ROW, THREE_OF_A_KIND_ROW}},
            {FULL_HOUSE_ROW,        FULL_HOUSE_POINTS,      FULL_HOUSES,        new Integer[]{THREE_OF_A_KIND_ROW, FULL_HOUSE_ROW}},
            {SMALL_STRAIGHT_ROW,    SMALL_STRAIGHT_POINTS,  SMALL_STRAIGHTS,    new Integer[]{SMALL_STRAIGHT_ROW}},
            {LARGE_STRAIGHT_ROW,    LARGE_STRAIGHT_POINTS,  LARGE_STRAIGHTS,    new Integer[]{SMALL_STRAIGHT_ROW, LARGE_STRAIGHT_ROW}},
            {YAHTZEE_ROW,           YAHTZEE_POINTS,         YAHTZEE,            new Integer[]{THREE_OF_A_KIND_ROW, FOUR_OF_A_KIND_ROW, YAHTZEE_ROW}},
        };
        // @formatter:on

        // Run the tests
        for (Object[] rowTest : rowTests)
        {
            testOneLowerSectionRow((int) rowTest[0], (int) rowTest[1], (Integer[][]) rowTest[2], (Integer[]) rowTest[3]);
        }
    }

    /**
     * Test the {@link ScoreGrid#getUpperPotentialScore(int, Dices)} for a set of dices.
     */
    @Test
    void getUpperPotentialScore()
    {
        /*
        The set of dices used to test the getUpperPotentialScore method.
        The format is the following:
        0. An array of dices values
        1 to 6. The score the player would get if he scored in the {1-6} row.
         */
        Object[][] setOfDices = {
            {new Integer[]{1, 2, 3, 4, 5}, 1, 2, 3, 4, 5, 0},
            {new Integer[]{6, 6, 3, 4, 3}, 0, 0, 6, 4, 0, 12},
            {new Integer[]{6, 6, 6, 6, 6}, 0, 0, 0, 0, 0, 30},
            {new Integer[]{5, 1, 2, 1, 5}, 2, 2, 0, 0, 10, 0},
            {new Integer[]{3, 3, 4, 3, 2}, 0, 2, 9, 4, 0, 0},
            {new Integer[]{5, 2, 5, 4, 6}, 0, 2, 0, 4, 10, 6},
        };

        for (Object[] setOfDice : setOfDices)
        {
            for (int i = 1; i < setOfDice.length; i++)
            {
                dices.setDices((Integer[]) setOfDice[0]);

                int row = i - 1;
                int expected = (int) setOfDice[i];
                int actual = scoreGrid.getUpperPotentialScore(row, dices);

                assertRowEquals(row, actual, expected);
            }
        }
    }

    /**
     * Test the {@link ScoreGrid#isFull()} method.
     */
    @Test
    void isFull()
    {
        assertFalse(scoreGrid.isFull(), "Score grid should not be full at the beginning");

        // Fill the scoreGrid (the dices are random, we don't care about the score)
        for (int i = 0; i < scoreGrid.getScoreSheet().length; i++)
        {
            scoreGrid.setScore(i, dices);
        }

        assertTrue(scoreGrid.isFull(), "Score grid is not correctly detected as full");

        // Remove a score
        scoreGrid.getScoreSheet()[2] = NO_SCORE;

        assertFalse(scoreGrid.isFull(), "Score grid should not be detected as " +
            "full when there is one NO_SCORE inside");
    }

    /**
     * Score 62 points in the upper section, in the Fours, Fives and Sixes rows.
     */
    private void score62UpperPoints()
    {
        // Make a total of points of 62
        Integer[][] diceSets = {
            new Integer[]{6, 6, 6, 6, 6}, // Total 6*5 = 30
            new Integer[]{5, 5, 5, 5, 1}, // Total 30 + 5*4 = 30 + 20 = 50
            new Integer[]{4, 4, 4, 1, 1}, // Total 50 + 4*3 = 50 + 12 = 62
        };

        // Score them
        for (Integer[] diceSet : diceSets)
        {
            dices.setDices(diceSet);
            scoreGrid.setScore(diceSet[0] - 1, dices);
        }
    }

    /**
     * Score a Yahtzee of the given value.
     *
     * @param value the value of the dices wanted for the Yahtzee
     */
    private void scoreYahtzee(int value)
    {
        // Get a Yahtzee from the Yahtzee's list
        dices.setDices(YAHTZEE[value - 1]);
        scoreGrid.setScore(YAHTZEE_ROW + UPPER_SECTION_SIZE, dices);
    }

    /**
     * Test the {@link ScoreGrid#getUpperBonus()} method.
     */
    @Test
    void getUpperBonus()
    {
        assertEquals(0, scoreGrid.getUpperBonus(), "Upper bonus should be 0 when score is 0");

        score62UpperPoints();
        assertEquals(0, scoreGrid.getUpperBonus(), "Upper bonus should be active ONLY for 63 upper points or more");

        scoreYahtzee(1);
        assertEquals(0, scoreGrid.getUpperBonus(), "Upper bonus should not depend on the lower section points");

        // Score one more point to get to 63 points
        dices.setDices(new Integer[]{1, 2, 2, 2, 2,});
        scoreGrid.setScore(0, dices);

        assertEquals(ScoreGrid.UPPER_BONUS_POINTS, scoreGrid.getUpperBonus(), "Upper bonus should be given at 63 points");

        // Score a bit more
        dices.setDices(new Integer[]{3, 3, 3, 3, 3});
        scoreGrid.setScore(2, dices);
        assertEquals(ScoreGrid.UPPER_BONUS_POINTS, scoreGrid.getUpperBonus(), "Upper bonus should be given when there is more than 63 points");
    }

    /**
     * Test the {@link ScoreGrid#getPointsBeforeUpperBonus()} method.
     */
    @Test
    void getPointsBeforeUpperBonus()
    {

        assertEquals(63, scoreGrid.getPointsBeforeUpperBonus(),
                     "When the score grid is empty, the player should have to score " +
                         ScoreGrid.UPPER_SECTION_MINIMUM + " to get the bonus");

        score62UpperPoints();
        assertEquals(1, scoreGrid.getPointsBeforeUpperBonus(),
                     "At 62 points, the player should only score 1 more point");

        scoreYahtzee(1);
        assertEquals(1, scoreGrid.getPointsBeforeUpperBonus(),
                     "Upper bonus should not depend on the lower section points");

        // Score one more point to get to 63 points
        dices.setDices(new Integer[]{1, 2, 2, 2, 2,});
        scoreGrid.setScore(0, dices);

        assertEquals(0, scoreGrid.getPointsBeforeUpperBonus(),
                     "At 63 points, the player should have 0 points to score left for the bonus");

        // Score a bit more
        dices.setDices(new Integer[]{3, 3, 3, 3, 3});
        scoreGrid.setScore(2, dices);
        assertEquals(0, scoreGrid.getPointsBeforeUpperBonus(),
                     "At more than 63 points, the player should have 0 points to score left for the bonus");
    }

    /**
     * Test the {@link ScoreGrid#getTotalScore()} method.
     */
    @Test
    void getTotalScore()
    {
        int expectedPoints = 0;
        assertEquals(expectedPoints, scoreGrid.getTotalScore(), "Total score should be 0 when the game starts");

        // Score in the lower section
        scoreYahtzee(1);
        expectedPoints += YAHTZEE_POINTS;
        assertEquals(expectedPoints, scoreGrid.getTotalScore(), "Total score should take lower section into account");

        // Score in the upper section, without triggering the upper bonus
        score62UpperPoints();
        expectedPoints += 62;
        assertEquals(expectedPoints, scoreGrid.getTotalScore(), "Total score should take upper section into account");

        // Score 1 point
        dices.setDices(new Integer[]{1, 2, 2, 2, 2});
        scoreGrid.setScore(0, dices);
        expectedPoints += 1 + ScoreGrid.UPPER_BONUS_POINTS;
        assertEquals(expectedPoints, scoreGrid.getTotalScore(),
                     "Total score should take upper bonus into account");

        // Get a Yahtzee bonus
        dices.setDices(YAHTZEE[0]);
        scoreGrid.scorePossibleYahtzeeBonus(dices);
        expectedPoints += YAHTZEE_BONUS_POINTS;
        assertEquals(expectedPoints, scoreGrid.getTotalScore(),
                     "Total score should take upper bonus into account");
    }

    /**
     * Test the {@link ScoreGrid#scorePossibleYahtzeeBonus(Dices)} method:
     * if the player score one Yahtzee then has another one, he gets a bonus.
     */
    @Test
    void scorePossibleYahtzeeBonus()
    {
        // Test if the bonus is given when it should

        assertEquals(0, scoreGrid.getYahtzeeBonuses(), "The score grid should start with no Yahtzee bonus");

        // Score a yahtzee of Ones
        scoreYahtzee(1);

        assertEquals(0, scoreGrid.getYahtzeeBonuses(), "One Yahtzee should not give a Yahtzee bonus");

        // Get a yahtzee of Twos, and check if the bonus is increased
        dices.setDices(YAHTZEE[1]);
        scoreGrid.scorePossibleYahtzeeBonus(dices);

        assertEquals(1, scoreGrid.getYahtzeeBonuses(), "Getting a Yahtzee after having successfully scored" +
            "in the Yahtzee section should give one bonus");

        // Now, check if the bonus is not given when it should not be
        scoreGrid = new ScoreGrid();
        dices.setDices(new Integer[]{1, 2, 3, 4, 5});

        // Score a 0 in the yahtzee section
        scoreGrid.setScore(YAHTZEE_ROW + UPPER_SECTION_SIZE, dices);

        // Get a yahtzee of Twos, and check if the bonus is NOT increased
        dices.setDices(YAHTZEE[1]);
        scoreGrid.scorePossibleYahtzeeBonus(dices);

        assertEquals(0, scoreGrid.getYahtzeeBonuses(), "Getting a Yahtzee after having already scored a 0" +
            "in the Yahtzee section should NOT give a bonus");
    }
}