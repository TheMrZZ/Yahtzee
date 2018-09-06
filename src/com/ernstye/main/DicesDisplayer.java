package com.ernstye.main;

import static com.ernstye.main.Constants.NUMBER_OF_DICES;

/**
 * A class used to display a dice roll.
 * Stores the dice images and convenience methods for an easy display.
 */
class DicesDisplayer
{

    /**
     *
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
     * Creates a displayer for specific dices
     *
     * @param dices_ the dices to display
     */
    DicesDisplayer(Dices dices_)
    {
        dices = dices_;
    }


    /**
     * Display the {@code dices} in a fancy way, using the {@link com.ernstye.main.DicesDisplayer#DICES_IMAGES}
     *
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
        Integer[] dicesFaces = dices.get();

        // The height & the width of the dices images
        final int HEIGHT = DICES_IMAGES[0].length;
        final int WIDTH = DICES_IMAGES[0][0].length();

        // The string used to separate dices
        final String DICES_SEPARATOR = "   ";

        /*
        We need to display the dices on an horizontal way.
        Therefore, we need to iterate on the rows first: we display the top of the 1st dice, then top of the 2nd dice...
        Then we keep going with the 2nd row, etc...
         */
        for (int row = 0; row < HEIGHT; row++)
        {
            for (int diceValue : dicesFaces)
            {
                // We get the correct image for the dice, we then display the corresponding row
                String[] diceImage = DICES_IMAGES[diceValue - 1];
                System.out.print(diceImage[row] + DICES_SEPARATOR);
            }
            System.out.println();
        }

        // Now we display the dices indexes under their images
        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            displayWhiteSpaces(WIDTH / 2 - 1);
            System.out.print("n°" + (i + 1));
            displayWhiteSpaces(WIDTH / 2 - 1);
            System.out.print(DICES_SEPARATOR);
        }
        System.out.println();
    }

    /**
     * Displays the specified number of white spaces
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
}
