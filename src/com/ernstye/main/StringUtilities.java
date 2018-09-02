package com.ernstye.main;

import java.util.Arrays;

/**
 * String Utilities class
 * Methods used for string - or arrays of strings - manipulation
 */
public class StringUtilities
{
    /**
     * Return the length of the longest string
     *
     * @param strings the array to search for the longest string
     * @return the longest string's length
     */
    static int getLongestStringLenght(String[] strings)
    {
        int maxLength = 0;
        for (String string : strings)
        {
            if (string.length() > maxLength)
            {
                maxLength = string.length();
            }
        }
        return maxLength;
    }

    /**
     * Creates a string of specified <code>length</code> filled with specified <code>character</code>
     *
     * @param character the character to fill the string with
     * @param length    the length of the final string
     * @return a string of given length filled with the specified character
     */
    static String stringFilledWith(char character, int length)
    {
        if (length < 0)
        {
            return "";
        }

        // Creates an array, fill it with the same character, then transform it into a String
        char[] chars = new char[length];
        Arrays.fill(chars, character);
        String filledString = new String(chars);
        return filledString;
    }
}
