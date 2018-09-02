package com.ernstye.main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.askNumber;

class Dices
{
    private Integer dices[];

    Dices()
    {
        dices = new Integer[NUMBER_OF_DICES];
        roll();
    }

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

    private ArrayList<Integer> verifyDuplicate(int dicesToRoll)
    {
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<Integer>();

        int i = 0;
        while (i < dicesToRoll)
        {
            int dice = askNumber(6);
            if (dicesEnteredByUser.contains(dice))
            {
                System.out.println("Sale con");
            } else
            {
                dicesEnteredByUser.add(dice);
                i++;
            }
        }
        return dicesEnteredByUser;
    }

    private void nextTurn()
    {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<Integer>();
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again?");
        dicesToRoll = scanner.nextInt();
        System.out.println("Which dices would you like to toss again?");
        for (int i = 0; i < dicesToRoll; i++)
        {
            dicesEnteredByUser = verifyDuplicate(dicesToRoll);
            //The dice chosen is being toss again
            dices[dicesEnteredByUser.get(i)] = random.nextInt(DICE_FACES) + 1;
        }


    }

    /*
    This method shows the toss of the 5 dices
     */
    void display()
    {
        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            System.out.println(i + ") " + dices[i]);
        }
    }
}
