package com.ernstye.main;

import java.util.Scanner;

public class Player
{
    private String name;
    private Dices dices;
    ScoreGrid scoreGrid;

    Player()
    {
        dices = new Dices();
        scoreGrid = new ScoreGrid();
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your name");
        name = scanner.nextLine();

    }

    Dices getDices()
    {
        return dices;
    }
}
