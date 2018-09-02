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

    void play(ScoreGrid scoreGrid)
    {
        int continue_ = 1;
        int i = 0;
        roll();
        scoreGrid.display(this);
        display();
        while (continue_ == 1 && i < 2)
        {

            System.out.println("Would you like to play again? 0: No, 1: Yes");
            continue_ = askNumber(2);

            if (continue_ == 1)
            {
                nextTurn();
                scoreGrid.display(this);
                display();
                i++;
            } else
            {
                continue_ = 0;
            }

        }
    }

    private void roll()
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
                System.out.println("You have already chosen this dice! Please enter another one :");
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
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<Integer>();
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again?");
        dicesToRoll = askNumber(1, 6);
        System.out.println("Which dices would you like to toss again?");
        dicesEnteredByUser = verifyDuplicate(dicesToRoll);
        for (int i = 0; i < dicesToRoll; i++)
        {
            //The dices chosen are being toss again
            dices[dicesEnteredByUser.get(i)] = random.nextInt(DICE_FACES) + 1;
        }


    }

    /*
    This method shows the toss of the 5 dices
     */
    void display()
    {
        System.out.println("This is the result of your roll :");
        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            System.out.println(i + ") " + dices[i]);
        }
    }
}
