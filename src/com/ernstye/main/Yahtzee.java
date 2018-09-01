package com.ernstye.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;
import static com.ernstye.main.Dices.*;

public class Yahtzee
{
    /**
     * A working Yahtzee implementation.
     * <p>
     * TODO:
     * - Implement a single-player working version of the dices
     * - Implement the upper section of the points
     * </p>
     */

    public static void main(String[] args)
    {
        System.out.println("== YAHTZEE ==");

        Dices dices=new Dices();
        dices.roll();
        dices.display();
        for (int i=0; i<2;i++)
        {
            System.out.println("\nTURN "+(i+2));
            dices.nextTurn();
            dices.display();
        }


    }

    public static void displayDices(int dices[])
    {
        for (int i=0; i<6;i++)
        {
            System.out.println(i+") "+ dices[i]);
        }
    }
}
