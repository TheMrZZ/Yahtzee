package com.ernstye.main;

import java.util.Random;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.StringUtilities.center;
import static com.ernstye.main.StringUtilities.mergeStrings;

/**
 * A class used to display a dice roll.
 * Stores the dice images and convenience methods for an easy display.
 */
class DicesDisplayer
{
    /**
     * ASCII images for each dice face.
     * Will display correctly with any monospaced font: Consolas, Monospaced, Source Code Pro, Hack...
     * <p>
     * All dices must have the same height and the same width for the program to work.
     */
    private final static String[][] DICES_IMAGES =
        {
            {
                " _________ ",
                "|         |",
                "|         |",
                "|    o    |",
                "|         |",
                "|_________|"
            },
            {
                " _________ ",
                "|         |",
                "|  o      |",
                "|         |",
                "|      o  |",
                "|_________|"
            },
            {
                " _________ ",
                "|         |",
                "|  o      |",
                "|    o    |",
                "|      o  |",
                "|_________|"
            },
            {
                " _________ ",
                "|         |",
                "|  o   o  |",
                "|         |",
                "|  o   o  |",
                "|_________|"
            },
            {
                " _________ ",
                "|         |",
                "|  o   o  |",
                "|    o    |",
                "|  o   o  |",
                "|_________|"
            },
            {
                " _________ ",
                "|         |",
                "|  o   o  |",
                "|  o   o  |",
                "|  o   o  |",
                "|_________|"
            },
        };

    private Dices dices;

    /**
     * Creates a displayer for specific dices.
     *
     * @param dices_ the dices to display
     */
    DicesDisplayer(Dices dices_)
    {
        dices = dices_;
    }


    /**
     * Display the {@code dices} in a fancy way, using the {@link DicesDisplayer#DICES_IMAGES}.
     * <p>
     * The format used is the following:
     * <pre>
     *  _________     _________     _________     _________     _________
     * |         |   |         |   |         |   |         |   |         |
     * |         |   |         |   |  o      |   |  o   o  |   |  o      |
     * |    o    |   |    o    |   |         |   |         |   |         |
     * |         |   |         |   |      o  |   |  o   o  |   |      o  |
     * |_________|   |_________|   |_________|   |_________|   |_________|
     *     n°1           n°2           n°3           n°4           n°5
     * </pre>
     */
    void display()
    {
        Integer[] dicesFaces = dices.getDices();

        // The height & the width of the dices images
        final int HEIGHT = DICES_IMAGES[0].length;
        final int WIDTH = DICES_IMAGES[0][0].length();

        // The string used to separate dices
        final String DICES_SEPARATOR = "   ";

        // The images of the dices
        String[][] diceImages = new String[dicesFaces.length][HEIGHT + 1];
        for (int i = 0; i < dicesFaces.length; i++)
        {
            Integer diceValue = dicesFaces[i];

            // Fill the first HEIGHT rows with the dice images, the last row will have the index
            System.arraycopy(DICES_IMAGES[diceValue - 1], 0, diceImages[i], 0, HEIGHT);
            diceImages[i][HEIGHT] = center("n°" + (i + 1), WIDTH);
        }

        // Display the dices in a horizontal way
        System.out.println(mergeStrings(diceImages, DICES_SEPARATOR));
    }

    /**
     * Displays the specified number of white spaces.
     *
     * @param number the number of white spaces to display
     */
    private void displayWhiteSpaces(int number)
    {
        for (int i = 0; i < number; i++)
        {
            System.out.print(" ");
        }
    }

    /**
     * Display a rolling dices animation.
     */
    void displayAnimation()
    {
        Random random = new Random();

        String dices_representation = "";
        for (Integer dice : dices.getDices())
        {
            dices_representation += DICES_UNICODE[dice - 1];
        }

        for (int dice = 0; dice < NUMBER_OF_DICES; dice++)
        {
            String representation = "Rolling... " + dices_representation.substring(0, dice);
            for (int anim = 0; anim < 20; anim++)
            {
                System.out.print(representation);
                System.out.print(DICES_UNICODE[random.nextInt(DICE_FACES)]);

                try
                {
                    Thread.sleep(15);
                }
                catch (InterruptedException ignored)
                {
                }

                System.out.print("\r");
            }
        }
    }
}
