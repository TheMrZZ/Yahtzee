package com.ernstye.main;

import java.util.Scanner;

/**
 * PLayer model object
 * contains one player who can play the game
 */
class Player
{
    private String name;
    private Dices dices;
    private ScoreGrid scoreGrid;

    /**
     * Create a player who owns a {@code scoreGrid}, {@code #NUMBER_OF_DICES} dices and has a {@code name}.
     */
    Player()
    {
        dices = new Dices();
        scoreGrid = new ScoreGrid();
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your name:");
        name = scanner.nextLine();

    }

    /**
     * Dices getter
     *
     * @return {@code dices}
     */
    Dices getDices()
    {

        return dices;
    }

    /**
     * Name getter
     * @return the {@code name} of the player
     */
    String getName()
    {
        return name;
    }

    /**
     * scoreGrid getter
     * @return the {@code scoreGrid}
     */
    ScoreGrid getScoreGrid()
    {
        return scoreGrid;
    }

    /**
     * let the Player play one turn, if the {@code scoreGrid} is full, show the final results of the Player
     */
    void play()
    {

        dices.play(scoreGrid);
        System.out.println();
        scoreGrid.score(dices);
        if (scoreGrid.isFull())
        {
            System.out.println("\n\n=== FINAL RESULTS PLAYER " + name + " ===\n\n");
            scoreGrid.display(null);
            System.out.println("You scored a total of " + scoreGrid.getTotalScore() + " points!");
        }
    }

    /**
     * get the total score of the scoreGrid
     * @return the total score of the player
     */
    int totalScore()
    {
        return scoreGrid.getTotalScore();
    }

}
