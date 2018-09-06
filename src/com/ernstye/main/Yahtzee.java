package com.ernstye.main;

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
        System.out.println("====== YAHTZEE ======");
        displayRules();
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

    public static void displayRules()
    {
        System.out.println("RULES : The game consists of a number of rounds. In each round, a player gets three rolls of the dice, although they can choose to end their turn after one or two rolls. After the first roll the player can save any dice they want and re-roll the other dice. This procedure is repeated after the second roll. The player has complete choice as to which dice to roll.");
    }
}
