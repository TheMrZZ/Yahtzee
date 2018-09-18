package com.ernstye.main;

/**
 * A class adding support for ANSI colors in terminal.
 */
final class Colors
{
    private static final String TEMPLATE = "\u001B[%dm";

    static final String BOLD      = String.format(TEMPLATE, 1);
    static final String ITALIC    = String.format(TEMPLATE, 3);
    static final String UNDERLINE = String.format(TEMPLATE, 4);

    static final String BLACK   = String.format(TEMPLATE, 30);
    static final String RED     = String.format(TEMPLATE, 31);
    static final String GREEN   = String.format(TEMPLATE, 32);
    static final String YELLOW  = String.format(TEMPLATE, 33);
    static final String BLUE    = String.format(TEMPLATE, 34);
    static final String MAGENTA = String.format(TEMPLATE, 35);
    static final String CYAN    = String.format(TEMPLATE, 36);
    static final String WHITE   = String.format(TEMPLATE, 37);

    static final String RESET = String.format(TEMPLATE, 0);
}

