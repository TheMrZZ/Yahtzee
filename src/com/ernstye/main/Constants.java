package com.ernstye.main;

/**
 * Collection of constants used for the game.
 * <p>All members of this class are immutable.</p>
 */
final class Constants
{
    /**
     * Used for the rows (in the score grid) the player didn't use for the moment
     */
    static final int NO_SCORE = -1;

    /**
     * The number of faces a dice has
     */
    static final int DICE_FACES = 6;

    /**
     * The number of dices we play with
     */
    static final int NUMBER_OF_DICES = 5;

    /**
     * The names of every line in the upper section
     */
    static final String[] UPPER_SECTION_ROWS = {
        "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes"
    };
}
