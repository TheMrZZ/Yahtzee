package com.ernstye.main;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

import static com.ernstye.main.Constants.*;

class Dices
{
    Dices()
    {
        dices = new Integer[NUMBER_OF_DICES];
        roll();
    }

    private Integer[] dices;

    Integer[] get()
    {
        return dices;
    }

    void roll()
    {
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            dices[i] = random.nextInt(DICE_FACES) + 1;
        }


    }

    void nextTurn()
    {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again?");
        dicesToRoll = scanner.nextInt();
        System.out.println("Which dices would you like to toss again?");
        for (int i = 0; i < dicesToRoll; i++)
        {
            int throwAgainDice = scanner.nextInt();
            //The dice chosen is being toss again
            dices[throwAgainDice] = random.nextInt(DICE_FACES) + 1;
        }


    }

    /**
     * This method shows the toss of the {@link com.ernstye.main.Constants#NUMBER_OF_DICES} dices
     */
    void display()
    {
        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            System.out.println(i + ") " + dices[i]);
        }
    }


}
