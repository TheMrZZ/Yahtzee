package com.ernstye.main;

import java.util.concurrent.TimeUnit;

import static com.ernstye.main.Constants.NUMBER_OF_DICES;

/**
 * A class used to display a dice roll
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
     * Display the {@code dices} in a fancy way, using the {@code DICES_IMAGES}
     */
    void display()
    {
        Integer[] dicesFaces = dices.get();
        final int HEIGHT = DICES_IMAGES[0].length;
        final int WIDTH = DICES_IMAGES[0][0].length();
        final String DICES_SEPARATOR = "   ";
        final String DICE_ANNOUNCEMENT = "";
        // Start by iterating on the rows
        for (int i = 0; i < HEIGHT; i++)
        {
            displayWhiteSpaces(DICE_ANNOUNCEMENT.length());
            for (int diceValue : dicesFaces)
            {
                System.out.print(DICES_IMAGES[diceValue - 1][i] + DICES_SEPARATOR);
            }
            System.out.println();
        }

        System.out.print(DICE_ANNOUNCEMENT);
        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            displayWhiteSpaces(WIDTH / 2 - 1);
            System.out.print("n°" + (i + 1));
            displayWhiteSpaces(WIDTH / 2 - 1);
            System.out.print(DICES_SEPARATOR);
        }
        System.out.println();

    }

    private void displayWhiteSpaces(int number)
    {
        for (int i = 0; i < number; i++)
        {
            System.out.print(" ");
        }
    }

    private void sleep(int milliseconds)
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (InterruptedException ignored)
        {
        }
    }
}
