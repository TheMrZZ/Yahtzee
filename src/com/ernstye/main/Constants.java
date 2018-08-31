package com.ernstye.main;

/**
 * Collection of constants used for the game.
 * <p>All members of this class are immutable.</p>
 */
public final class Constants
{
    /** Used for the rows (in the score grid) the player didn't use for the moment */
    public static final int NO_SCORE = -1;

    // The number of faces a dice has
    public static final int DICE_FACES = 6;

    // The number of dices we play with
    public static final int NUMBER_OF_DICES = 5;

    public static final String[] DICE_IMAGES = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};

    public static final String[] UPPER_SECTION_ROWS = {
            "Ones", "Twos", "Threes", "Fours", "Fives", "Sixes"
    };
}
