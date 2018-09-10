package com.ernstye.main;

/**
 * Collection of constants used for the game.
 * <p>All members of this class are immutable.</p>
 */
final class Constants
{
    /**
     * Used for the rows (in the score grid) the player didn't use for the moment.
     */
    static final int NO_SCORE = -1;

    /**
     * The number of faces a dice has.
     */
    static final int DICE_FACES = 6;


    /**
     * The number of rows in the upper section.
     * Equals to the number of faces of one dice.
     */
    static final int UPPER_SECTION_SIZE = DICE_FACES;

    /**
     * The number of dices we play with.
     */
    static final int NUMBER_OF_DICES = 5;

    /**
     * The names of every line in the upper section.
     */
    static final String[] SCORE_SHEET_ROWS = {
        "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",
        "Three of a kind", "Four of a kind", "Full house",
        "Small Straight", "Large straight", "Chance", "YAHTZEE",
    };

    // The number of the row (in the lower section)
    static final int
        THREE_OF_A_KIND_ROW = 0,
        FOUR_OF_A_KIND_ROW = 1,
        FULL_HOUSE_ROW = 2,
        SMALL_STRAIGHT_ROW = 3,
        LARGE_STRAIGHT_ROW = 4,
        CHANCE_ROW = 5,
        YAHTZEE_ROW = 6;


    /**
     * The points given for a full house.
     */
    static final int FULL_HOUSE_POINTS = 25;

    /**
     * The points given for a small straight.
     */
    static final int SMALL_STRAIGHT_POINTS = 30;

    /**
     * The points given for a large straight.
     */
    static final int LARGE_STRAIGHT_POINTS = 40;

    /**
     * The points given for a yahtzee.
     */
    static final int YAHTZEE_POINTS = 50;


}
