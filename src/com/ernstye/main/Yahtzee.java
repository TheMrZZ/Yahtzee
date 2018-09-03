package com.ernstye.main;

/**
 * A working single-player implementation of Yahtzee's upper section.
 *
 * @author Florian ERNST, Céline YE
 */
public class Yahtzee
{
    public static void main(String[] args)
    {
        startGame();
    }

    /**
     * Starts the game
     */
    private static void startGame()
    {
        int turnNumber = 1;
        System.out.println("== YAHTZEE ==");
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
}
