package com.ernstye.main;

import java.util.Arrays;

/**
 * String Utilities class.
 * Methods used for string - or arrays of strings - manipulation
 */
final class StringUtilities
{
    /**
     * Return the length of the longest string.
     *
     * @param strings the array to search for the longest string
     * @return the longest string's length
     */
    static int getLongestStringLength(String[] strings)
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
     * Creates a string of specified {@code length} filled with specified {@code character}.
     *
     * @param character the character to fill the string with
     * @param length    the length of the final string
     * @return a string of given length filled with the specified character
     */
    static String stringFilledWith(char character, int length)
    {
        if (length <= 0)
        {
            return "";
        }

        // Creates an array, fill it with the same character, then transform it into a String
        char[] chars = new char[length];
        Arrays.fill(chars, character);
        String filledString = new String(chars);
        return filledString;
    }

    /**
     * Center a string with white spaces.
     * If the number of white spaces to be inserted is odd, the remaining white space will be added to the left.
     *
     * <p>
     * Ex:
     * <pre>
     *     |  String  |
     * or   |  String |
     * </pre>
     *
     * @param string the string to center
     * @param width  the total width of the centered string
     * @return the centered string
     */
    static String center(String string, int width)
    {
        if (string.length() >= width)
        {
            return string;
        }

        int diff = width - string.length();
        int leftSpaces = (diff + 1) / 2;
        int rightSpaces = diff / 2;

        String centered = stringFilledWith(' ', leftSpaces) +
                          string +
                          stringFilledWith(' ', rightSpaces);

        return centered;
    }
}
