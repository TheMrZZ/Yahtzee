package com.ernstye.main;

import java.io.File;
import java.nio.charset.StandardCharsets;
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
        while (true)
        {
            menu();
        }
    }

    /**
     * Starts one game, and play until the grid is full.
     */
    private static void startOneGame()
    {

        Players players = new Players();
        players.playGame();
    }

    /**
     * Yahtzee menu, let the user choose what he wants to do
     */
    private static void menu()
    {
        int userInput;
        // Menu display
        System.out.println("============================");
        System.out.println("|         YAHTZEE          |");
        System.out.println("============================");
        System.out.println("|       1. Rules           |");
        System.out.println("|       2. Play            |");
        System.out.println("|       3. See records     |");
        System.out.println("|       4. Exit            |");
        System.out.println("============================");
        System.out.println("Select an option :");
        userInput = askNumber(1, 5);
        switch (userInput)
        {
            case 1:
                displayRules();
                break;
            case 2:
                startOneGame();
                break;
            case 3:
                displayRecords();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                break;


        }
    }

    /**
     * Display the rules of the Yahtzee game.
     * Rules are kept in the {@code rules.txt} file.
     */
    private static void displayRules()
    {
        File file =
            new File("rules.txt");

        try
        {
            Scanner inputFile = new Scanner(file, StandardCharsets.UTF_8.name());
            //while the text file has more text
            while (inputFile.hasNextLine())
            {
                //print it
                System.out.println(inputFile.nextLine());
            }
            inputFile.close();
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("The rules.txt file was not found");
        }

        System.out.println();

    }

    /**
     * Display previous records of the game
     */
    private static void displayRecords()
    {
        File file =
            new File("records.txt");

        try
        {
            Scanner inputFile = new Scanner(file, StandardCharsets.UTF_8.name());
            //while the text file has more text
            while (inputFile.hasNextLine())
            {
                //print it
                System.out.println(inputFile.nextLine());
            }
            inputFile.close();
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("The records.txt file was not found");
        }

        System.out.println();


    }
}

