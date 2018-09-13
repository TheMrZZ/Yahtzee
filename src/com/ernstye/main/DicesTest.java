package com.ernstye.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class testing the {@link Dices} class.
 *
 * @see ScoreGridTest ScoreGridTest for instructions on installing JUnit5.
 */
class DicesTest
{
    private Dices dices;

    /**
     * Setup the tests by making sure the dices are not null.
     */
    @BeforeEach
    void setUp()
    {
        dices = new Dices();
    }

    /**
     * Test the {@link Dices#sum()} method.
     */
    @Test
    void sum()
    {
        /*
        A set of dices rolls.
        Format is the following:
        0. The dices values
        1. Their expected sum
        */
        Object[][] dicesAndSums = {
            {new Integer[]{1, 2, 3, 4, 5}, 15},
            {new Integer[]{6, 6, 6, 6, 6}, 30},
            {new Integer[]{1, 5, 5, 1, 5}, 17},
            {new Integer[]{4, 6, 4, 6, 4}, 24},
            {new Integer[]{3, 3, 4, 3, 3}, 16}
        };

        for (Object[] dicesAndSum : dicesAndSums)
        {
            dices.setDices((Integer[]) dicesAndSum[0]);

            int expected = (int) dicesAndSum[1];
            int actual = dices.sum();
            String error = "Sum of " + Arrays.toString(dices.getDices()) + " should be " + expected + ", not " + actual;

            assertEquals(expected, actual, error);
        }
    }

    /**
     * Test the {@link Dices#getOccurrencesOf(int)} method.
     */
    @Test
    void getOccurrencesOf()
    {
        /*
         An array of dices next to their occurrences.
         The format of each row is the following:
         0. The dices values
         1. The occurrences of each value (index 0 = occurrences of Ones, etc...)
        */
        final Integer[][][] DICES_AND_OCCURRENCES = {
            {new Integer[]{1, 2, 3, 4, 5}, new Integer[]{1, 1, 1, 1, 1, 0}},
            {new Integer[]{1, 1, 1, 4, 5}, new Integer[]{3, 0, 0, 1, 1, 0}},
            {new Integer[]{6, 6, 6, 6, 6}, new Integer[]{0, 0, 0, 0, 0, 5}},
            {new Integer[]{4, 4, 3, 3, 4}, new Integer[]{0, 0, 2, 3, 0, 0}},
        };

        for (Integer[][] dicesAndOccurrence : DICES_AND_OCCURRENCES)
        {
            dices.setDices(dicesAndOccurrence[0]);
            Integer[] expectedOccurrences = dicesAndOccurrence[1];

            for (int i = 0; i < 6; i++)
            {
                int expected = expectedOccurrences[i];
                int actual = dices.getOccurrencesOf(i + 1);
                String error = "Dices " + Arrays.toString(dices.getDices()) + " have " + expected + " occurrences of "
                               + (i + 1) + ", but " + actual + " occurrences were found";

                assertEquals(expected, actual, error);
            }
        }
    }

    /**
     * Test the {@link Dices#getLongestStraightSize()}.
     */
    @Test
    void getLongestStraightSize()
    {
        /*
        An array of dices values, and their longest straight length
        The format is the following:
        0. Dices values
        1. The longest straight possible in these dices
         */
        final Object[][] diceSets = {
            {new Integer[]{1, 2, 3, 4, 5}, 5},
            {new Integer[]{1, 1, 3, 3, 5}, 1},
            {new Integer[]{2, 3, 4, 5, 6}, 5},
            {new Integer[]{2, 3, 3, 2, 4}, 3},
            {new Integer[]{2, 1, 2, 1, 2}, 2},
            {new Integer[]{1, 4, 2, 3, 6}, 4},
            {new Integer[]{1, 2, 4, 5, 6}, 3}
        };

        for (Object[] diceSet : diceSets)
        {
            dices.setDices((Integer[]) diceSet[0]);

            int expected = (int) diceSet[1];
            int actual = dices.getLongestStraightSize();
            String error = "Dices " + Arrays.toString(dices.getDices()) + " should have a longest straight length of " +
                           expected + ", but a length of " + actual + " was found.";

            assertEquals(expected, actual, error);
        }
    }
}