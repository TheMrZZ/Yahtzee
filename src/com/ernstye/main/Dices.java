package com.ernstye.main;

import java.util.Scanner;
import java.util.Random;
import static com.ernstye.main.Constants.*;

public class Dices
{
    public Dices()
    {

    }
    private int dices[];


    public void roll()
    {
        dices =new int[NUMBER_OF_DICES];
        Random random= new Random();
        System.out.println("YAHTZEE GAME !");
        System.out.println("TURN 1");

        for (int i=0; i<NUMBER_OF_DICES;i++)
        {
            dices[i]=random.nextInt(DICE_FACES)+1;

        }


    }

    public void nextTurn()
    {
        Random random=new Random();
        Scanner scanner = new Scanner(System.in);
        int dicesToRoll;
        System.out.println("How many dices do you want to toss again?");
        dicesToRoll=scanner.nextInt();
        System.out.println("Which dices would you like to toss again?");
        for (int i=0;i<dicesToRoll;i++)
        {
            int throwAgainDice=scanner.nextInt();
            //The dice chosen is being toss again
            dices[throwAgainDice]=random.nextInt(DICE_FACES)+1;
        }


    }

    /*
    This method shows the toss of the 5 dices
     */
    public void display()
    {
        for (int i=0; i<NUMBER_OF_DICES;i++)
        {
            System.out.println(i+") "+ dices[i]);
        }
    }
}
