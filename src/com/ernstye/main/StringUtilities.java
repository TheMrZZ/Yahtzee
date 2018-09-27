package com.ernstye.main;

import java.util.Arrays;
import java.util.regex.Matcher;

import static com.ernstye.main.Colors.COLOR_NAMES;
import static com.ernstye.main.Colors.COLOR_PATTERN;

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
        // First, we need to skip every ANSI patterns - they are used for colors
        Matcher matcher = COLOR_PATTERN.matcher(string);
        int charactersToSkip = 0;
        while (matcher.find())
        {
            charactersToSkip += matcher.group(0).length();
        }

        int length = string.length() - charactersToSkip;

        if (length >= width)
        {
            return string;
        }

        int diff = width - length;
        int leftSpaces = (diff + 1) / 2;
        int rightSpaces = diff / 2;

        String centered = stringFilledWith(' ', leftSpaces) +
            string +
            stringFilledWith(' ', rightSpaces);

        return centered;
    }

    /**
     * Colorize a string in the given color/style.
     *
     * @param original the string to colorize
     * @param color    the color to apply. If {@code null}, no color will be given.
     * @param style    the style to apply. If {@code null}, no style will be given.
     * @return a string with the given color and the given style
     */
    static String colorize(String original, String color, String style)
    {
        if (color == null && style == null)
        {
            return original;
        }

        String colorized = original;
        if (style != null)
        {
            colorized = COLOR_NAMES.get(style.toUpperCase()) + colorized;
        }
        if (color != null)
        {
            colorized = COLOR_NAMES.get(color.toUpperCase()) + colorized;
        }

        colorized = colorized + Colors.RESET;
        return colorized;
    }


    /**
     * Colorize an int in the given color/style. Convenience wrapper for {@link #colorize(String, String, String)}.
     *
     * @param original the number to colorize
     * @param color    the color to apply. If {@code null}, no color will be given.
     * @param style    the style to apply. If {@code null}, no style will be given.
     * @return a string containing the number, with the given color and the given style
     */
    static String colorize(int original, String color, String style)
    {
        return colorize(String.valueOf(original), color, style);
    }

    /**
     * Merge arrays of strings of the same height, like if the arrays were displayed in a horizontal way.
     * Example:
     * <pre>
     *  _________
     * |         |
     * |  o   o  |
     * |         |
     * |  o   o  |
     * |_________|
     *      +
     *  _________
     * |         |
     * |  o      |
     * |         |
     * |      o  |
     * |_________|
     *
     *    gives
     *  _________     _________
     * |         |   |         |
     * |  o   o  |   |  o      |
     * |         |   |         |
     * |  o   o  |   |      o  |
     * |_________|   |_________|
     * </pre>
     *
     * @param arraysOfStrings the array of strings to merge. All arrays should have the same width.
     * @param separator       the separator between arrays
     * @return the merged string
     */
    static String mergeStrings(String[][] arraysOfStrings, String separator)
    {
        final int HEIGHT = arraysOfStrings[0].length;

        String result = "";

        for (int row = 0; row < arraysOfStrings[0].length; row++)
        {
            for (String[] array : arraysOfStrings)
            {
                result += array[row] + separator;
            }
            result += "\n";
        }

        return result;
    }


    /**
     * Merge arrays of strings of the same height, like if the arrays were displayed in a horizontal way.
     * Example:
     * <pre>
     *  _________\n
     * |         |\n
     * |  o   o  |\n
     * |         |\n
     * |  o   o  |\n
     * |_________|
     *      +
     *  _________\n
     * |         |\n
     * |  o      |\n
     * |         |\n
     * |      o  |\n
     * |_________|
     *
     *    gives
     *  _________     _________\n
     * |         |   |         |\n
     * |  o   o  |   |  o      |\n
     * |         |   |         |\n
     * |  o   o  |   |      o  |\n
     * |_________|   |_________|
     * </pre>
     *
     * @param arrayOfStrings the array of strings to merge. All arrays should have the same width. Strings rows are
     *                       delimited by newlines {@code \n}.
     * @param separator      the separator between arrays
     * @return the merged string
     */
    static String mergeStrings(String[] arrayOfStrings, String separator)
    {
        String[][] arraysOfStrings = new String[arrayOfStrings.length][];

        for (int i = 0; i < arrayOfStrings.length; i++)
        {
            String string = arrayOfStrings[i];

            arraysOfStrings[i] = string.split("\\r?\\n");
        }

        return mergeStrings(arraysOfStrings, separator);
    }

    /**
     * Get the real length of a string (without the colors characters).
     *
     * @param string the string
     * @return the real length of the string
     */
    static int realLength(String string)
    {
        string = string.replaceAll(COLOR_PATTERN.toString(), "");
        return string.length();
    }
}
