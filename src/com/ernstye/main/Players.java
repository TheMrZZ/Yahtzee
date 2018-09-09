package com.ernstye.main;


import java.util.ArrayList;
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
        if (isDraw())
        {
            ArrayList<Integer> drawPlayers = drawPlayers();
            for (Integer player : drawPlayers)
            {
                System.out.print(players[player].getName() + " have a draw! ");
            }
        } else
        {
            System.out.print(players[getWinner()].getName() + " has won!");
        }


    }

    void turnPlayers(int turnNumber)
    {
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            System.out.println("\n=== TURN " + turnNumber + " ===\n");
            System.out.println("PLAYER " + (playerNumber + 1) + ": " + player.getName() + "'S TURN");
            player.play();
        }

    }

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



