package com.ernstye.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.ernstye.main.UserInput.askNumber;
import static com.ernstye.main.Constants.NO_SCORE;
import static com.ernstye.main.Constants.NUMBER_OF_DICES;
import static com.ernstye.main.Constants.UPPER_SECTION_ROWS;

public class ScoreGrid
{
    private int[] upperSection;

    ScoreGrid()
    {
        upperSection = new int[NUMBER_OF_DICES];
        Arrays.fill(upperSection, NO_SCORE);
    }

    void score(int[] dices)
    {
        System.out.println("Score points in which row?");
        for (int i = 0; i < upperSection.length; i++)
        {
            if (upperSection[i] == NO_SCORE)
            {
                System.out.print((i + 1) + ") " + UPPER_SECTION_ROWS[i] + "\t");
                System.out.println(getRowScore(i) + " potential points");
            }
        }

        int row = askNumber(1, upperSection.length + 1, "Choose a section") - 1;
        addScore(row, dices);
    }

    int getRowScore(int row)
    {
        return upperSection[row];
    }

    private void addScore(int row, int[] dices)
    {
        int points;
        int correctDices = Collections.frequency(Arrays.asList(dices), row+1);
        upperSection[row] = correctDices * row;
    }
}
