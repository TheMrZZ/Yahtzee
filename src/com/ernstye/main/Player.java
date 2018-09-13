package com.ernstye.main;

/**
 * PLayer model object.
 * contains one player who can play the game
 */
class Player
{
    /**
     * The name of the player.
     */
    private String name;

    /**
     * The {@value Constants#NUMBER_OF_DICES} dices the player have.
     */
    private Dices dices;

    /**
     * The score grid of the player.
     */
    private ScoreGrid scoreGrid;

    /**
     * Create a new player with an empty {@link Player#scoreGrid}.
     *
     * @param name_ the name of the player
     */
    Player(String name_)
    {
        dices = new Dices();
        scoreGrid = new ScoreGrid();
        name = name_;
    }

    /**
     * Dices getter.
     *
     * @return {@link Player#dices}
     */
    Dices getDices()
    {
        return dices;
    }

    /**
     * Name getter.
     *
     * @return the {@link Player#name} of the player
     */
    String getName()
    {
        return name;
    }

    /**
     * scoreGrid getter.
     *
     * @return the {@link Player#scoreGrid}
     */
    ScoreGrid getScoreGrid()
    {
        return scoreGrid;
    }

    /**
     * Let the Player play one turn.
     * If the {@link Player#scoreGrid} is full, show the final results of the Player.
     *
     * @param playerNumber the number of the player, used to display final result
     */
    void playOneTurn(int playerNumber)
    {
        dices.play(scoreGrid);
        System.out.println();
        scoreGrid.score(dices);
    }

    /**
     * Get the total score of the scoreGrid.
     *
     * @return the total score of the player
     */
    int totalScore()
    {
        return scoreGrid.getTotalScore();
    }
}
