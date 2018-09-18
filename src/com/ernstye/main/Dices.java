package com.ernstye.main;

import java.util.*;

import static com.ernstye.main.Constants.DICES_UNICODE;
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
     * Array of values of the dices.
     * (e.g. [1, 2, 1, 2, 3] means the player rolled two Ones, two Twos and one Three).
     */
    private Integer dices[];

    /**
     * Create and roll the {@value Constants#NUMBER_OF_DICES}  dices randomly
     */
    Dices()
    {
        dices = new Integer[NUMBER_OF_DICES];
        roll();
    }

    /**
     * Dices getter.
     *
     * @return the array of {@link Dices#dices}
     */
    Integer[] getDices()
    {
        return dices;
    }

    /**
     * Give 0 to 2 chances for the player to roll again the dices he has chosen.
     *
     * @param scoreGrid the scoreGrid of the player
     */
    void play(ScoreGrid scoreGrid)
    {
        roll();

        int dicesToRoll;
        int rollNumber = 0;

        // Display first roll
        displayRoll(scoreGrid, rollNumber);

        do
        {
            rollNumber++;

            // Ask if the player wants a new roll
            dicesToRoll = askNumber(0, NUMBER_OF_DICES + 1, "How many dices do you want to roll again?");

            // If the player wants to roll other dices
            if (dicesToRoll > 0)
            {
                // Roll the dices he wants
                nextTurn(dicesToRoll);

                // Display new roll
                displayRoll(scoreGrid, rollNumber);
            }
        }
        while (dicesToRoll > 0 && rollNumber < 2); // If the player wants to roll again the dices or if he hasn't played 3 times yet, continue the loop
    }

    /**
     * Display the current roll number.
     *
     * @param rollNumber the current roll number
     */
    private void displayRollNumber(int rollNumber)
    {
        System.out.println("\n=== ROLL " + (rollNumber) + " ===");
    }

    /**
     * Roll the {@value Constants#NUMBER_OF_DICES} dices randomly
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
     * Ask the user the dices' index to be rerolled, and verify there isn't duplicates in the dices chosen.
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
     * Let the player choose the dices he want to roll again, then roll them randomly.
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
     * Show the values of the {@value Constants#NUMBER_OF_DICES} dices
     *
     * @param rollNumber the number of the actual roll
     */
    private void display(int rollNumber)
    {
        DicesDisplayer dicesDisplayer = new DicesDisplayer(this);
        Random random = new Random();

        String dices_representation = "";
        for (Integer dice : dices)
        {
            dices_representation += DICES_UNICODE[dice - 1];
        }
        for (int dice = 0; dice < NUMBER_OF_DICES; dice++)
        {
            String representation = "Rolling... " + dices_representation.substring(0, dice);
            for (int anim = 0; anim < 20; anim++)
            {
                System.out.print(representation);
                System.out.print(DICES_UNICODE[random.nextInt(DICE_FACES)]);

                try
                {
                    Thread.sleep(15);
                }
                catch (InterruptedException ignored)
                {
                }

                System.out.print("\r");
            }
        }

        System.out.println("This is the result of roll n°" + rollNumber + ":");
        dicesDisplayer.display();
    }

    /**
     * Display the current roll, and the score grid.
     *
     * @param scoreGrid  the score grid to display
     * @param rollNumber the current roll number
     */
    private void displayRoll(ScoreGrid scoreGrid, int rollNumber)
    {
        displayRollNumber(rollNumber + 1);
        System.out.println(scoreGrid.getDisplay(this));
        display(rollNumber + 1);
    }

    /**
     * Get the sum of every dices.
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
     * Get the number of dices equals to {@code value}.
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
     * Get a list of occurrences for each possible {@link Constants#DICE_FACES}
     *
     * @param sorted if false, then the index 0 will correspond to the number of Aces, index 1 number of Twos etc...
     *               if true, then the list will be sorted in descending order
     * @return a list of occurrences of size {@value Constants#DICE_FACES}
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

        // Remove duplicates from the list by passing values into a set, then back into the list
        Set<Integer> temporarySet = new HashSet<>(diceList);
        diceList = new ArrayList<>(temporarySet);

        diceList.sort(null);

        int max = 1;
        int actual = 1;
        for (int i = 1; i < diceList.size(); i++)
        {
            // If the current number is the previous number + 1, then the straight length is larger by 1.
            // Else, start a new straight.
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
     * Set the dices values to the given integers values.
     *
     * @param dices the new value for {@code dices}
     */
    void setDices(Integer[] dices)
    {
        this.dices = dices;
    }
}
