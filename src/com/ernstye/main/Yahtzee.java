package com.ernstye.main;

import java.io.File;
import java.util.Scanner;

import static com.ernstye.main.UserInput.askNumber;

/**
 * A working single-player implementation of Yahtzee's upper section.
 *
 * @author Florian ERNST, Céline YE
 */
class Yahtzee
{

    /**
     * Entry point for the program. Launches the Yahtzee.
     *
     * @param args not currently used
     */
    public static void main(String[] args)
    {
        startGame();
    }

    /**
     * Starts a game and ask player for a new one, until the player asks to stop.
     */
    private static void startGame()
    {

        int continue_ = 1;
        while (continue_ == 1)
        {
            startOneGame();

            System.out.println("\n\n");
            continue_ = askNumber(0, 2, "Would you like to play again? 0:No 1:Yes");
            System.out.println("\n\n");
        }
    }

    /**
     * Starts one game, and play until the grid is full.
     */
    private static void startOneGame()
    {
        int showRules;
        System.out.println("====== YAHTZEE ======\n");
        System.out.println("Do you want to know the rules? 0:No 1:Yes");

        showRules = askNumber(0, 2);
        if (showRules == 1)
        {
            displayRules();
        }

        Players players = new Players();
        players.playGame();
    }


    /**
     * Display the rules of the Yahtzee game.
     * Rules are kept in the {@code rules.txt} file.
     */
    private static void displayRules()
    {
        File file =
            new File("rules.txt");
        Scanner scanner;
        try
        {
            scanner = new Scanner(file);
            //while the text file has more text
            while (scanner.hasNextLine())
            {
                //print it
                System.out.println(scanner.nextLine());
            }
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("The rules.txt file was not found");
        }

        System.out.println();
    }
}

