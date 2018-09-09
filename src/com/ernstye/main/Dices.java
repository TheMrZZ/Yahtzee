package com.ernstye.main;

import java.util.ArrayList;
import java.util.Random;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Dices model object.
 * <p>
 * contains the dices for the user to play with
 */

import static com.ernstye.main.Constants.DICE_FACES;
import static com.ernstye.main.Constants.NUMBER_OF_DICES;
class Dices
{
    /**
     * Create and roll the {@value com.ernstye.main.Constants#NUMBER_OF_DICES}  dices randomly
     */
    private Integer dices[];

    Dices()
    {
        dices = new Integer[NUMBER_OF_DICES];

        roll();
    }

    /**
     * Dices getter
     *
     * @return the array of dices
     */
    Integer[] getDices()
    {
        return dices;
    }

    /**
     * Give 0 to 2 chances for the player to roll again the dices he has chosen
     *
     * @param scoreGrid the scoreGrid of the player
     */
    void play(ScoreGrid scoreGrid)
    {
        int dicesToRoll = 1;
        int i = 0;
        roll();
        scoreGrid.display(this);
        display(i + 1);
        //if the player wants to roll again the dices or if he hasn't played 3 times yet, continue the loop
        while (dicesToRoll > 0 && i < 2)
        {
            dicesToRoll = askNumber(0, NUMBER_OF_DICES, "How many dices do you want to roll again?");

            if (dicesToRoll > 0)
            {
                nextTurn(dicesToRoll);
                System.out.println();
                scoreGrid.display(this);
                i++;
                display(i + 1);
            }
        }
    }

    /**
     * roll the {@value com.ernstye.main.Constants#NUMBER_OF_DICES}  dices randomly
     */
    private void roll()
    {
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            dices[i] = random.nextInt(DICE_FACES) + 1;
        }
    }

    /**
     * Ask the user the dices' index to be rerolled, and verify there isn't duplicates in the dices chosen
     *
     * @param dicesToRoll Number of dices to be rerolled
     * @return a list of the dices' index to be rerolled
     */
    private ArrayList<Integer> verifyDuplicate(int dicesToRoll)
    {
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<Integer>();

        int i = 0;
        while (i < dicesToRoll)
        {
            int dice = askNumber(1, NUMBER_OF_DICES + 1) - 1;
            //if the user has already entered the dice's index, ask him to choose another one
            if (dicesEnteredByUser.contains(dice))
            {
                System.out.println("You have already chosen this dice! Please enter another one :");
            }
            //else the dice's index is added to the list
            else
            {
                dicesEnteredByUser.add(dice);
                i++;
            }
        }
        return dicesEnteredByUser;
    }

    /**
     * Reroll randomly the dices the player has chosen
     *
     * @param dicesToRoll Number of dices to be rerolled
     */
    private void nextTurn(int dicesToRoll)
    {
        Random random = new Random();
        System.out.println("Which dices would you like to toss again?");
        ArrayList<Integer> dicesEnteredByUser = verifyDuplicate(dicesToRoll);
        for (int i = 0; i < dicesToRoll; i++)
        {
            //The dices chosen are being toss again
            dices[dicesEnteredByUser.get(i)] = random.nextInt(DICE_FACES) + 1;
        }
    }

    /**
     * This method shows the roll of the {@value com.ernstye.main.Constants#NUMBER_OF_DICES}  dices
     */
    void display(int rollNumber)
    {
        System.out.println("=== ROLL " + (rollNumber) + " ===");
        System.out.println("This is the result of your roll :");
        DicesDisplayer dicesDisplayer = new DicesDisplayer(this);
        dicesDisplayer.display();
        /*for (int i = 0; i < NUMBER_OF_DICES; i++)
        {
            System.out.println(i+1 + ")" + DICE_IMAGES[dices[i]-1]);
        }*/
    }
}
