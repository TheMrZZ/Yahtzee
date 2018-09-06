package com.ernstye.main;

import javax.xml.bind.annotation.XmlType;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.ernstye.main.Constants.*;

public class DicesDisplayer
{
    String[][] dicesImages =
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

    DicesDisplayer(Dices dices_)
    {
        dices = dices_;
    }


    void display()
    {
        displayAnimatedDice("This is the result of your roll :");

        Integer[] dicesFaces = dices.get();
        final int HEIGHT = dicesImages[0].length;
        final int WIDTH = dicesImages[0][0].length();
        final String DICES_SEPARATOR = "   ";
        final String DICE_ANNOUNCEMENT = "";
        // Start by iterating on the rows
        for (int i = 0; i < HEIGHT; i++)
        {
            displayWhiteSpaces(DICE_ANNOUNCEMENT.length());
            for (int diceValue : dicesFaces)
            {
                System.out.print(dicesImages[diceValue - 1][i] + DICES_SEPARATOR);
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

    private void displayAnimatedDice(String message)
    {
        Random random = new Random();
        for (int i = 0; i < 800; i++)
        {
            System.out.print(message + " " + DICE_IMAGES[random.nextInt(DICE_FACES)]);
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {

            }
            System.out.print("\r");
        }
        System.out.println(message);

    }

    private void displayWhiteSpaces(int number)
    {
        for (int i = 0; i < number; i++)
        {
            System.out.print(" ");
        }
    }
}
