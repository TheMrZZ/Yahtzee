package com.ernstye.main;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides several methods for easier interactions with the user.
 * Mostly input functions.
 */
final class UserInput
{
    private static Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

    /**
     * Ask a number to the user, strictly greater than 0.
     *
     * @return a number greater than 0
     */
    static int askPositiveNumber()
    {
        int number;

        do
        {
            number = askNumberRaw("");

            if (number <= 0)
            {
                System.out.println(number + " is not a positive number");
            }
        } while (number <= 0);

        return number;
    }

    /**
     * Returns number entered by the user between 0 and {@code max}.
     *
     * @param max upper bound, not included
     * @return the number entered by the user
     */
    static int askNumber(int max)
    {
        return askNumber(0, max);
    }

    /**
     * Returns number entered by the user between {@code min} and {@code max}.
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
     * Returns number entered by the user between {@code min} and {@code max}.
     * The {@code message} will be shown before asking the number.
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
                System.out.println(number + " is not between " + min + " and " + (max - 1) + ".");
            }
        } while (!validInput);

        return number;
    }

    /**
     * Returns a number entered by the user.
     * Shows the {@code message} before asking a number.
     * Check if the user entered a number, else will ask again.
     *
     * @param message shown before asking the number
     * @return number entered by the user.
     */

    private static int askNumberRaw(String message)
    {
        String userInput;
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
                // We strip the leading and trailing whitespaces from the user input string
                userInput = scanner.nextLine().trim();
                number = Integer.parseInt(userInput);
                return number;
            }
            catch (NumberFormatException e)
            {
                System.out.println("This is not a number.");
            }
        }
    }


    /**
     * Ask the user several numbers, and verify there is no duplicates.
     * If {@code allowReset} is true, then the values will be taken between {@code min-1} and {@code max}, and
     * in this case, if the user enters {@code min-1}, {@code null} will be returned.
     *
     * @param size           the number of numbers to ask
     * @param min            the minimum of each value (included).
     * @param max            the maximum of each value (excluded)
     * @param duplicateError an error message to display if the user enters a duplicate
     * @param allowReset     if true, then the user will be able to enter {@code min-1}, in which case {@code null}
     *                       will be returned.
     * @return a list of the unique numbers between {@code min} and {@code max} of given {@code size}.
     * {@code null} if allowReset is true, and the user enters {@code min-1}.
     */
    static ArrayList<Integer> askUniqueNumbers(int size, int min, int max, String duplicateError, boolean allowReset)
    {
        ArrayList<Integer> dicesEnteredByUser = new ArrayList<>();

        if (allowReset)
        {
            min--;
        }

        int i = 0;
        while (i < size)
        {
            int dice = askNumber(min, max);

            if (allowReset && dice == min)
            {
                return null;
            }

            // If the user has already entered the number, ask him to choose another one
            if (dicesEnteredByUser.contains(dice))
            {
                System.out.println(duplicateError);
            }
            else
            {
                dicesEnteredByUser.add(dice);
                i++;
            }
        }
        return dicesEnteredByUser;
    }

    /**
     * Just wait until the user pressed enter.
     */
    static void askPressEnter()
    {
        System.out.print("> ");
        scanner.nextLine();
    }

    /**
     * Ask the user an {@code int} or a single character.
     * If the input is an {@code int}, then the boundaries will be checked.
     * If the input is a {@code char}, then the char presence in {@code possibleChars} will be checked.
     *
     * @param min           the minimum of the number (in case of an {@code int} input), included
     * @param max           the maximum of the number (in case of an {@code int} input), excluded
     * @param possibleChars the possible chars (in case of a {@code char} input)
     * @param msg           the message to display before the input. If {@code null}, then nothing will be displayed.
     * @return an Object, either a number or a String of length 1.
     */
    static Object askNumberOrChar(int min, int max, String possibleChars, String msg)
    {
        possibleChars = possibleChars.toUpperCase();
        String error = "Input should be a number between " + min + " and " + (max - 1);
        if (!msg.equals(""))
        {
            error = error + ", or one of these characters: " + possibleChars;
        }

        while (true)
        {
            if (msg != null)
            {
                System.out.println(msg);
            }
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Test for a number and its boundaries.
            try
            {
                int number = Integer.valueOf(input);

                if (min <= number && number < max)
                {
                    return number;
                }

                // The input is a number, but an incorrect one - ask another input
                continue;
            }
            catch (NumberFormatException ignored)
            {
            }

            /*
            Here, the input is not a number.
            If the input is already a single character, and is one of the possible chars, return it.
            */
            if (input.length() == 1 && possibleChars.contains(input.toUpperCase()))
            {
                return input;
            }

            System.out.println(error);
        }
    }
}
