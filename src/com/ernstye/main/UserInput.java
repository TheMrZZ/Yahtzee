package com.ernstye.main;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides several methods for easier interactions with the user.
 * Mostly input functions.
 */
class UserInput
{
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Returns number entered by the user between 0 and <code>max</code>
     *
     * @param max upper bound, not included
     * @return the number entered by the user
     */
    static int askNumber(int max)
    {
        return askNumber(0, max);
    }

    /**
     * Returns number entered by the user between <code>min</code> and <code>max</code>
     *
     * @param min lower bound, included
     * @param max upper bound, not included
     * @return the number entered by the user
     */
    static int askNumber(int min, int max)
    {
        return askNumber(min, max, "");
    }

    /**
     * Returns number entered by the user between <code>min</code> and <code>max</code>.
     * The <code>message</code> will be shown before asking the number.
     *
     * @param min     lower bound, included
     * @param max     upper bound, not included
     * @param message shown before asking the number
     * @return the number entered by the user
     */
    static int askNumber(int min, int max, String message)
    {
        int number;
        boolean validInput;

        /*
        Make sure the input is between the bounds.
        Input validation is inside the askNumberRaw function.
        */
        do
        {
            number = askNumberRaw(message);
            validInput = (min <= number && number < max);
            if (!validInput)
            {
                System.out.println(number + " is not between " + min + " and " + max + ".");
            }
        } while (!validInput);

        return number;
    }

    /**
     * Returns a number entered by the user.
     * Shows the <code>message</code> before asking a number.
     * Check if the user entered a number, else will ask again.
     *
     * @param message shown before asking the number
     * @return number entered by the user.
     */

    private static int askNumberRaw(String message)
    {
        int number;

        while (true)
        {
            if (!message.equals(""))
            {
                System.out.println(message);
            }
            System.out.print("> ");

            // Check for invalid input (e.g. 'abc' instead of a number)
            try
            {
                number = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("This is not a number.");
                scanner.nextLine(); // Cleans the buffer
                continue;
            }

            return number;
        }
    }
}
