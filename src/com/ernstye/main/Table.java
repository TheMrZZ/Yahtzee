package com.ernstye.main;

/**
 * A table used for display purposes.
 */
public class Table
{
    private int totalWidth;
    private String leftColumnFormat;
    private String middleColumnFormat;
    private String rightColumnFormat;
    private String rowSeparator;

    Table(String leftColumnFormat_, String middleColumnFormat_,
          String rightColumnFormat_, int totalWidth_)
    {
        leftColumnFormat = leftColumnFormat_;
        middleColumnFormat = middleColumnFormat_;
        rightColumnFormat = rightColumnFormat_;
        totalWidth = totalWidth_;
        rowSeparator = StringUtilities.stringFilledWith('-', totalWidth);
    }

    void display()
    {

    }
}
