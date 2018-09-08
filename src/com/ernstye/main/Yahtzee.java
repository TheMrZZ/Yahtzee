package com.ernstye.main;

import java.io.File;
import java.util.Scanner;
import static com.ernstye.main.UserInput.askNumber;

/**
 * A working single-player implementation of Yahtzee's upper section.
 *
 * @author Florian ERNST, Céline YE
 */
public class Yahtzee
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
        int turnNumber = 1;
        Scanner scanner = new Scanner(System.in);
        int showRules;
        System.out.println("====== YAHTZEE ======\n");
        System.out.println("Do you want to know the rules? 0:No 1:Yes");
        showRules = scanner.nextInt();
        if (showRules == 1)
        {
            displayRules();
        }
        Dices dices = new Dices();
        ScoreGrid scoreGrid = new ScoreGrid();

        while (!scoreGrid.isFull())
        {
            System.out.println("\n=== TURN " + turnNumber + " ===");
            dices.play(scoreGrid);

            System.out.println();
            scoreGrid.score(dices);

            turnNumber++;
        }

        System.out.println("\n\n=== FINAL RESULTS ===\n\n");
        scoreGrid.display(null);
        System.out.println("You scored a total of " + scoreGrid.getTotalScore() + " points!");
    }

    /**
     * Display the rules of the Yahtzee game
     * Open a txt file where the rules are read
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
                //print t
                System.out.println(scanner.nextLine());
            }

        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("The rules.txt was not found");
        }
    }
}

