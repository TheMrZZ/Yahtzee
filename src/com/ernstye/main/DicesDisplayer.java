package com.ernstye.main;

import static com.ernstye.main.Constants.*;

public class DicesDisplayer
{
    String[][] dicesImages =
        {
            {
                "┌──────┐",
                "│     │",
                "│  o  │",
                "│     │",
                "└──────┘"
            },

            {
                "┌──────┐",
                "│ o   │",
                "│     │",
                "│   o │",
                "└──────┘"
            },

            {
                "┌──────┐",
                "│ o   │",
                "│  o  │",
                "│   o │",
                "└──────┘"
            },

            {
                "┌──────┐",
                "│ o o │",
                "│     │",
                "│ o o │",
                "└──────┘"
            },

            {
                "┌──────┐",
                "│ o o │",
                "│  o  │",
                "│ o o │",
                "└──────┘"
            },

            {
                "┌──────┐",
                "│ o o │",
                "│ o o │",
                "│ o o │",
                "└──────┘"
            }
        };

    Dices dices;

    DicesDisplayer(Dices dices_)
    {
        dices = dices_;
    }

    void display()
    {
        Integer[] dicesFaces = dices.get();
        for (int i = 0; i < dicesFaces.length; i++)
        {

        }
    }
}
