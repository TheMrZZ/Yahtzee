package com.ernstye.main;


import java.util.Scanner;

import static com.ernstye.main.UserInput.askNumber;

public class Players
{
    private Player players[];
    private int numberOfPlayers;

    Players()
    {
        Scanner scanner = new Scanner(System.in);
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

    void playGame()
    {
        int i = 0;
        int turnNumber = 1;
        while (!players[players.length - 1].getScoreGrid().isFull())
        {
            turnPlayers(turnNumber);
            turnNumber++;
        }

        System.out.print(players[winner()].getName() + " has won!");

    }

    void turnPlayers(int turnNumber)
    {
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            System.out.println("\n=== TURN " + turnNumber + " ===\n");
            System.out.println(player.getName() + "'S TURN");
            player.getDices().play(player.getScoreGrid());

            System.out.println();
            player.getScoreGrid().score(player.getDices());

            if (player.getScoreGrid().isFull())
            {
                System.out.println("\n\n=== FINAL RESULTS PLAYER " + player.getName() + " ===\n\n");
                player.getScoreGrid().display(null);
                System.out.println("You scored a total of " + player.getScoreGrid().getTotalScore() + " points!");
            }
        }

    }

    int winner()
    {
        int max = 0;
        int winner = 0;
        for (int player = 0; player < players.length; player++)
        {
            if (max < players[player].getScoreGrid().getTotalScore())
            {
                max = players[player].getScoreGrid().getTotalScore();
                winner = player;
            }

        }
        return winner;

    }

}

        

