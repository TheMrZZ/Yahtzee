package com.ernstye.main;


import java.util.ArrayList;
import java.util.Scanner;

import static com.ernstye.main.UserInput.askNumber;

/**
 * Players model object
 * contains all the players playing the game
 */
public class Players
{

    private Player players[];
    private int numberOfPlayers;

    /**
     * Create the new players
     */
    Players()
    {
        System.out.println("Please enter the number of players");
        //TODO ASKPOSITIVE NUMBER
        numberOfPlayers = askNumber(5);

        players = new Player[numberOfPlayers];
        for (int i = 0; i < players.length; i++)
        {
            System.out.print("Player " + (i + 1) + ", ");
            players[i] = new Player();

        }
    }

    /**
     * Let the players play the game until the scoreGrid is not full, show the winner/or all the players that have a draw
     */
    void playGame()
    {

        int turnNumber = 1;
        while (!players[players.length - 1].getScoreGrid().isFull())
        {
            turnPlayers(turnNumber);
            turnNumber++;
        }
        //
        if (isDraw())
        {
            ArrayList<Integer> drawPlayers = drawPlayers();
            for (int i = 0; i < drawPlayers.size(); i++)
            {
                Integer player = drawPlayers.get(i);
                if (i != 0)
                {
                    System.out.print(" and ");
                }
                System.out.print(players[player].getName());

            }
            System.out.print(" have a draw!");
        } else
        {
            System.out.print(players[getWinner()].getName() + " has won!");
        }


    }

    /**
     * Playing one turn for each player
     *
     * @param turnNumber One game has 13 turns, show the turn number
     */
    void turnPlayers(int turnNumber)
    {

        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            System.out.println("\n=== TURN " + turnNumber + " ===\n");
            System.out.println("PLAYER " + (playerNumber + 1) + ", It's " + player.getName() + "'s turn!");
            player.play();
        }

    }

    /**
     * get the player that won the Yahtzee game, having the most points at the end
     * @return the winner of the game
     */
    int getWinner()
    {
        int max = 0;
        int winner = 0;
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (max < player.totalScore())
            {
                max = player.totalScore();
                winner = playerNumber;
            }

        }
        return winner;

    }

    /**
     * In case of a draw, find all the players that have a draw
     * @return all the players that are winners
     */
    ArrayList<Integer> drawPlayers()
    {

        ArrayList<Integer> drawPlayers = new ArrayList<Integer>();
        drawPlayers.add(getWinner());
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (playerNumber != getWinner() && player.totalScore() == players[getWinner()].totalScore())
            {
                drawPlayers.add(playerNumber);
            }

        }
        return drawPlayers;
    }

    /**
     * Find out if there's a draw in the game
     * @return true if there's a draw, false otherwise
     */
    boolean isDraw()
    {
        boolean isDraw = false;
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (playerNumber != getWinner() && player.totalScore() == players[getWinner()].totalScore())
            {
                isDraw = true;
            }

        }
        return isDraw;
    }
}


