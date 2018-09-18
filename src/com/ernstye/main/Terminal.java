package com.ernstye.main;

/**
 * A class adding support for ANSI colors in terminal.
 */
final class Terminal
{
    private static final String ANSI_TEMPLATE  = "\u001B[%s%s";
    private static final String COLOR_TEMPLATE = String.format(ANSI_TEMPLATE, "%s", "m");

    static final String BOLD      = String.format(COLOR_TEMPLATE, 1);
    static final String ITALIC    = String.format(COLOR_TEMPLATE, 3);
    static final String UNDERLINE = String.format(COLOR_TEMPLATE, 4);

    static final String BLACK   = String.format(COLOR_TEMPLATE, 30);
    static final String RED     = String.format(COLOR_TEMPLATE, 31);
    static final String GREEN   = String.format(COLOR_TEMPLATE, 32);
    static final String YELLOW  = String.format(COLOR_TEMPLATE, 33);
    static final String BLUE    = String.format(COLOR_TEMPLATE, 34);
    static final String MAGENTA = String.format(COLOR_TEMPLATE, 35);
    static final String CYAN    = String.format(COLOR_TEMPLATE, 36);
    static final String WHITE   = String.format(COLOR_TEMPLATE, 37);

    static final String RESET = String.format(COLOR_TEMPLATE, 0);
}

