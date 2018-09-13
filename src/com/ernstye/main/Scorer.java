package com.ernstye.main;

import java.util.List;

import static com.ernstye.main.Constants.*;

/**
 * Scorer model object.
 * <p>
 * Calculates the different possible scores from a set of dices.
 */
class Scorer
{
    private Dices dices;

    /**
     * Creates a scorer with no dices in it.
     */
    Scorer()
    {
        dices = new Dices();
    }

    /**
     * Set the {@link Scorer#dices} value.
     *
     * @param dices_ the dices
     */
    void setDices(Dices dices_)
    {
        dices = dices_;
    }

    /**
     * Returns the potential score of a player, in the Three Of A Kind row (at least 3 times the same dice value).
     *
     * @return the potential score the player would get by scoring a Three Of A Kind
     */
    int getThreeOfAKindScore()
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
     * @return the potential score the player would get by scoring a Four Of A Kind
     */
    int getFourOfAKindScore()
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
     * @return the potential score the player would get by scoring a Full House
     */
    int getFullHouseScore()
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
     * @return the potential score the player would get by scoring a Small Straight
     */
    int getSmallStraightScore()
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
     * @return the potential score the player would get by scoring a Large Straight
     */
    int getLargeStraightScore()
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
     * @return the potential score the player would get by scoring a Yahtzee
     */
    int getYahtzeeScore()
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
     * Return the potential score of a player, in the Chance row.
     *
     * @return the potential score the player would get by scoring into the Chance row
     */
    int getChanceScore()
    {
        return dices.sum();
    }

    /**
     * Return the potential score of a player, if he scored in one of the upper section's row.
     *
     * @param row the row of the upper section to score in
     * @return the potential score the player would get by scoring into this upper section's row
     */
    int getUpperRowScore(int row)
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
}
