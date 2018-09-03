package com.ernstye.main;

import java.util.ArrayList;
import java.util.Random;

import static com.ernstye.main.Constants.DICE_FACES;
import static com.ernstye.main.Constants.NUMBER_OF_DICES;
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
        int dicesToRoll = 1;
        int i = 0;
        roll();
        scoreGrid.display(this);
        display();
        while (dicesToRoll > 0 && i < 2)
        {

            dicesToRoll = askNumber(0, 6, "How many dices do you want to toss again?");

            if (dicesToRoll > 0)
            {
                nextTurn(dicesToRoll);
                scoreGrid.display(this);
                display();
                i++;
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

    private void nextTurn(int dicesToRoll)
    {
        Random random = new Random();
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<Integer>();
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
            System.out.println(i + ")" + dices[i]);
        }
    }
}
