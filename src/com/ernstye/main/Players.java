package com.ernstye.main;

import java.util.Scanner;

import static com.ernstye.main.UserInput.askNumber;

public class Players
{
    private Player players[];
    private int playerNumber;

    Players()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the number of players");
        playerNumber = askNumber(1);
        for (int i = 0; i < playerNumber; i++)
        {
            System.out.print("Player " + (i + 1) + ",");
            players[i] = new Player();
        }

    }


}
