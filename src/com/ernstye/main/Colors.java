package com.ernstye.main;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A class adding support for ANSI colors in terminal.
 */
final class Colors
{
    private static final String ANSI_TEMPLATE = "\u001B[%s%s";
    private static final String COLOR_TEMPLATE = String.format(ANSI_TEMPLATE, "%s", "m");

    static final Pattern COLOR_PATTERN = Pattern.compile("\u001B\\[\\d+m");

    static final String BOLD = String.format(COLOR_TEMPLATE, 1);
    static final String ITALIC = String.format(COLOR_TEMPLATE, 3);
    static final String UNDERLINE = String.format(COLOR_TEMPLATE, 4);

    static final String BLACK = String.format(COLOR_TEMPLATE, 30);
    static final String RED = String.format(COLOR_TEMPLATE, 31);
    static final String GREEN = String.format(COLOR_TEMPLATE, 32);
    static final String YELLOW = String.format(COLOR_TEMPLATE, 33);
    static final String BLUE = String.format(COLOR_TEMPLATE, 34);
    static final String MAGENTA = String.format(COLOR_TEMPLATE, 35);
    static final String CYAN = String.format(COLOR_TEMPLATE, 36);
    static final String WHITE = String.format(COLOR_TEMPLATE, 37);

    static final String RESET = String.format(COLOR_TEMPLATE, 0);


    static final Map<String, String> COLOR_NAMES;

    static
    {
        COLOR_NAMES = new HashMap<>();
        COLOR_NAMES.put("BOLD", BOLD);
        COLOR_NAMES.put("ITALIC", ITALIC);
        COLOR_NAMES.put("UNDERLINE", UNDERLINE);

        COLOR_NAMES.put("BLACK", BLACK);
        COLOR_NAMES.put("RED", RED);
        COLOR_NAMES.put("GREEN", GREEN);
        COLOR_NAMES.put("YELLOW", YELLOW);
        COLOR_NAMES.put("BLUE", BLUE);
        COLOR_NAMES.put("MAGENTA", MAGENTA);
        COLOR_NAMES.put("CYAN", CYAN);
        COLOR_NAMES.put("WHITE", WHITE);
    }
}

