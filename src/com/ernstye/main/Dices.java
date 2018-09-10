package com.ernstye.main;

import java.util.*;

import static com.ernstye.main.Constants.DICE_FACES;
import static com.ernstye.main.Constants.NUMBER_OF_DICES;
import static com.ernstye.main.UserInput.askNumber;

/**
 * Dices model object.
 * <p>
 * contains the dices for the user to play with
 */
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
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<>();

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
    private void display(int rollNumber)
    {
        System.out.println("=== ROLL " + (rollNumber) + " ===");
        System.out.println("This is the result of your roll :");
        DicesDisplayer dicesDisplayer = new DicesDisplayer(this);
        dicesDisplayer.display();
    }

    /**
     * Get the sum of every dices
     *
     * @return the sum of all dices
     */
    int sum()
    {
        int total = 0;
        for (int value : dices)
        {
            total += value;
        }
        return total;
    }

    /**
     * Get the number of dices equals to {@code value}
     *
     * @param value the value to look for
     * @return the number of dices with the wanted {@code value}
     */
    int getOccurrencesOf(int value)
    {
        int numberOfDices = 0;
        for (Integer dice : dices)
        {
            if (dice == value)
            {
                numberOfDices++;
            }
        }

        return numberOfDices;
    }

    /**
     * Get a list of occurrences for each possible {@link com.ernstye.main.Constants#DICE_FACES}
     *
     * @param sorted if false, then the index 0 will correspond to the number of Aces, index 1 number of Twos etc...
     *               if true, then the list will be sorted in descending order
     * @return a list of occurrences of size {@value com.ernstye.main.Constants#DICE_FACES}
     */
    List<Integer> getAllOccurrences(boolean sorted)
    {
        // First get the number of each faces in the list
        List<Integer> diceOccurrences = Arrays.asList(new Integer[DICE_FACES]);
        for (int i = 0; i < DICE_FACES; i++)
        {
            diceOccurrences.set(i, getOccurrencesOf(i + 1));
        }

        if (sorted)
        {
            diceOccurrences.sort(Collections.reverseOrder());
        }

        return diceOccurrences;
    }

    /**
     * Get the size of the longest straight in the current dices.
     * A straight is a consecutive sequence of dice (e.g. 1, 2, 3 ; 2, 3, 4, 5, 6 ; 3, 4, 5)
     *
     * @return the size of the longest straight in the current dices
     */
    int getLongestStraightSize()
    {
        List<Integer> diceList = Arrays.asList(dices.clone());
        diceList.sort(null);

        int max = 1;
        int actual = 1;
        for (int i = 1; i < diceList.size(); i++)
        {
            // If the current number is the previous number + 1
            if (diceList.get(i) == diceList.get(i - 1) + 1)
            {
                actual++;
                if (actual > max)
                {
                    max = actual;
                }
            } else
            {
                actual = 1;
            }
        }

        return max;
    }

    /**
     * <strong>
     * Should not be used in production.
     * Only present for testing purposes.
     * </strong>
     * Set the dices values to the given integers.
     *
     * @param dices the new value for {@code dices}
     */
    void setDices(Integer[] dices)
    {
        this.dices = dices;
    }
}
