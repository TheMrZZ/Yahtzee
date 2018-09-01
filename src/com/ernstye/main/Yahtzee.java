package com.ernstye.main;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

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
        dices=nextTurn(dices);
        displayDices(dices);
    }

    public static int[] startDices()
    {
        int dices[]=new int[6];
        System.out.println("YAHTZEE GAME !");
        Random random= new Random();
        System.out.println("Result of your toss :");

        for (int i=0; i<6;i++)
        {
            dices[i]=random.nextInt(6)+1;

        }
        return dices;

    }

    public static int[] nextTurn(int dices[])
    {
        Random random=new Random();
        Scanner scanner = new Scanner(System.in);
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again");
        dicesToRoll=scanner.nextInt();
        System.out.println("Which dices would you like to throw again?");
        for (int i=0;i<dicesToRoll;i++)
        {
            int throwAgainDice=scanner.nextInt();
            dices[throwAgainDice]=random.nextInt(6)+1;
        }
        return dices;


    }

    public static void displayDices(int dices[])
    {
        for (int i=0; i<6;i++)
        {
            System.out.println(i+") "+ dices[i]);
        }
    }

    /** Create the empty upper section: 6 rows for each possible scores
     *
     * @return upper section filled with {@link com.ernstye.main.Constants#NO_SCORE}
     */
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
