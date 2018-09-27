package com.ernstye.main;

import java.util.*;

import static com.ernstye.main.Constants.*;
import static com.ernstye.main.UserInput.*;

/**
 * Dices model object.
 * <p>
 * contains the dices for the user to play with
 */
class Dices
{
    /**
     * A roll number of this value means that the player used a bonus roll.
     */
    static final int JOKER_BONUS_ROLL = -1;

    /**
     * Array of values of the dices.
     * (e.g. [1, 2, 1, 2, 3] means the player rolled two Ones, two Twos and one Three).
     */
    private Integer dices[];

    private DicesDisplayer displayer;

    private int numberOfJokers;

    /**
     * Create and roll the {@value Constants#NUMBER_OF_DICES}  dices randomly.
     */
    Dices()
    {
        dices = new Integer[NUMBER_OF_DICES];
        displayer = new DicesDisplayer(this);
        roll();
        numberOfJokers = 3;
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
     * @param players   the players
     */
    void play(ScoreGrid scoreGrid, Players players)
    {
        roll();

        boolean keepGoing;
        int rollNumber = 0;

        do
        {
            keepGoing = doNextRoll(scoreGrid, rollNumber, players);
            rollNumber++;
        }
        while (keepGoing && rollNumber < 3); // If the player wants to roll again the dices or if he hasn't played 3 times yet, continue the loop
    }

    /**
     * Start the next roll.
     *
     * @param scoreGrid  the score grid of the current player
     * @param rollNumber the current roll number
     * @param players    the players
     * @return true if the player wants to keep playing, false if he doesn't
     */
    boolean doNextRoll(ScoreGrid scoreGrid, int rollNumber, Players players)
    {
        if (rollNumber == JOKER_BONUS_ROLL)
        {
            numberOfJokers--;
        }

        // Before the first roll, we don't have anything to do
        if (rollNumber > 0 || rollNumber == JOKER_BONUS_ROLL)
        {
            // Roll new dices
            boolean keepGoing = nextTurn();
            if (!keepGoing)
            {
                return false;
            }
        }

        // Display new roll
        displayRoll(scoreGrid, rollNumber, players);
        return true;
    }

    /**
     * Display the current roll number.
     *
     * @param rollNumber the current roll number
     */
    private void displayRollNumber(int rollNumber)
    {
        if (rollNumber == JOKER_BONUS_ROLL)
        {
            System.out.println("\n=== !! BONUS ROLL !! ===");
        }
        else
        {
            System.out.println("\n=== ROLL " + (rollNumber) + " ===");
        }
    }

    /**
     * Roll the {@value Constants#NUMBER_OF_DICES} dices randomly.
     */
    private void roll()
    {
        List<Integer> allDices = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_DICES; i++)
        {
            allDices.add(i);
        }
        roll(allDices);
    }

    /**
     * Roll the given dices randomly.
     *
     * @param dicesToRoll the indexes of the dices to roll, with values going from 1 to {@value Constants#NUMBER_OF_DICES}
     */
    private void roll(List<Integer> dicesToRoll)
    {
        Random random = new Random();

        for (int i = 1; i <= NUMBER_OF_DICES; i++)
        {
            if (dicesToRoll.contains(i))
            {
                dices[i - 1] = random.nextInt(DICE_FACES) + 1;
            }
        }
    }

    /**
     * Roll the given dices but only even numbers ie 2,4,6.
     *
     * @param dicesToRoll the indexes of the dices to roll, with values going from 1 to {@value Constants#NUMBER_OF_DICES}
     */
    private void rollEvenDices(List<Integer> dicesToRoll)
    {
        roll(dicesToRoll);
        for (int i = 1; i <= NUMBER_OF_DICES; i++)
        {
            if (dicesToRoll.contains(i) && dices[i - 1] % 2 == 1)
            {
                dices[i - 1] += 1;
            }
        }
    }

    /**
     * Roll the given dices but only odd numbers, ie 1,3,5.
     *
     * @param dicesToRoll the indexes of the dices to roll, with values going from 1 to {@value Constants#NUMBER_OF_DICES}
     */
    private void rollOddDices(List<Integer> dicesToRoll)
    {
        roll(dicesToRoll);
        for (int i = 1; i <= NUMBER_OF_DICES; i++)
        {
            if (dicesToRoll.contains(i) && dices[i - 1] % 2 == 0)
            {
                // only roll
                dices[i - 1] -= 1;
            }
        }
    }

    /**
     * Ask the user to enter dices indexes.
     *
     * @param dicesToRoll the number of dices to roll
     * @param topMessage  the message to display before asking for indexes
     * @return a list of dices indexes entered by the user, without duplicated.
     */
    private List<Integer> enterDices(int dicesToRoll, String topMessage)
    {
        List<Integer> dicesEnteredByUser;

        System.out.println(topMessage);

        dicesEnteredByUser = askUniqueNumbers(dicesToRoll, 1, NUMBER_OF_DICES + 1,
                                              "You have already chosen this dice! Please enter another one :",
                                              true);

        if (dicesEnteredByUser == null)
        {
            System.out.println("Erasing previous choices...");
        }

        return dicesEnteredByUser;
    }

    /**
     * Let the player choose the dices he want to roll again, then roll them randomly.
     * If the player doesn't want to roll dices anymore, return false.
     *
     * @return true if the user need another roll, false if he doesn't
     */
    private boolean nextTurn()
    {
        Object input;
        int dicesToRoll;
        List<Integer> dicesEnteredByUser = null;
        int jokerType = 0;
        String possibleChars = "J";
        String basicMessage = "How many dices do you want to roll again?";
        String msg = basicMessage + " Enter 'J' if you want to use a joker (" + numberOfJokers + " left)";

        if (numberOfJokers == 0)
        {
            msg = basicMessage;
            possibleChars = "";
        }

        while (dicesEnteredByUser == null)
        {
            // Ask if the player wants a new roll
            input = askNumberOrChar(0, NUMBER_OF_DICES + 1, possibleChars, msg);
            if ("J".equals(input))
            {
                numberOfJokers--;
                System.out.println("Which joker would you like to use?");
                System.out.println("1)Roll even dices\n2)Roll odd dices");
                jokerType = askNumber(1, 4);
                dicesToRoll = askNumber(0, NUMBER_OF_DICES + 1, basicMessage);
            }
            else
            {
                dicesToRoll = (int) input;
            }

            if (dicesToRoll == 0)
            {
                return false;
            }

            dicesEnteredByUser = enterDices(dicesToRoll, "Which dices would you like to toss again? (enter 0 if you made a mistake)");
        }

        // The chosen dices are being toss again
        switch (jokerType)
        {
            case 0:
                roll(dicesEnteredByUser);
                break;
            case 1:
                rollEvenDices(dicesEnteredByUser);
                break;
            case 2:
                rollOddDices(dicesEnteredByUser);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Show the values of the {@value Constants#NUMBER_OF_DICES} dices.
     *
     * @param rollNumber the number of the actual roll
     */
    private void display(int rollNumber)
    {
        if (rollNumber == JOKER_BONUS_ROLL)
        {
            System.out.println("This is the result of bonus roll:");
        }
        else
        {
            System.out.println("This is the result of roll n°" + rollNumber + ":");
        }
        displayer.display();
    }

    /**
     * Display the current roll, and the score grid.
     *
     * @param scoreGrid  the score grid to display
     * @param rollNumber the current roll number
     * @param players    the players
     */
    private void displayRoll(ScoreGrid scoreGrid, int rollNumber, Players players)
    {
        int realRoll = rollNumber + 1;
        if (rollNumber == JOKER_BONUS_ROLL)
        {
            realRoll = JOKER_BONUS_ROLL;
        }

        displayRollNumber(realRoll);
        if (NEED_DICE_ANIMATION)
        {
            displayer.displayAnimation();
        }
        System.out.println(scoreGrid.getDisplay(this, players));
        display(realRoll);
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
     * Get a list of occurrences for each possible {@link Constants#DICE_FACES}.
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
            }
            else
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

    /**
     * Return the {@link #numberOfJokers}.
     *
     * @return the number of jokers.
     */
    int getNumberOfJokers()
    {
        return numberOfJokers;
    }
}
