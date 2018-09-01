package com.ernstye.main;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.ernstye.main.Constants.*;

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

        int[] upperSection = createUpperSection();

        int dices[]=startDices();
        displayDices(dices);
        for (int i=0; i<2;i++)
        {
            System.out.println("\nTURN "+(i+2));
            nextTurn(dices);
            displayDices(dices);
        }

    }

    /*
    First turn: the 5 dices are being tossed randomly
     */
    public static int[] startDices()
    {
        int dices[]=new int[NUMBER_OF_DICES];
        Random random= new Random();
        System.out.println("YAHTZEE GAME !");
        System.out.println("TURN 1");

        for (int i=0; i<NUMBER_OF_DICES;i++)
        {
            dices[i]=random.nextInt(DICE_FACES)+1;

        }
        return dices;

    }

    public static void nextTurn(int dices[])
    {
        Random random=new Random();
        Scanner scanner = new Scanner(System.in);
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again?");
        dicesToRoll=scanner.nextInt();
        System.out.println("Which dices would you like to toss again?");
        for (int i=0;i<dicesToRoll;i++)
        {
            int throwAgainDice=scanner.nextInt();
            //The dice chosen is being toss again
            dices[throwAgainDice]=random.nextInt(DICE_FACES)+1;
        }


    }

    /*
    This method shows the toss of the 5 dices
     */
    public static void displayDices(int dices[])
    {
        for (int i=0; i<NUMBER_OF_DICES;i++)
        {
            System.out.println(i+") "+ dices[i]);
        }
    }

    // Create the empty upper section: 6 rows for each possible scores
    public static int[] createUpperSection()
    {
        int[] upperSection = new int[NUMBER_OF_DICES];
        Arrays.fill(upperSection, NO_SCORE);
        return upperSection;
    }

    public static void scorePoints(int[] upperSection, int[] dices)
    {
        System.out.println("Score points in which section?");
        for (int i = 0; i < upperSection.length; i++)
        {
            System.out.println(i+1 + ") " + UPPER_SECTION_ROWS[i]);
        }
    }

}
