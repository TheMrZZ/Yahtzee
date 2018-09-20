package com.ernstye.main;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ernstye.main.UserInput.askPositiveNumber;
import static com.ernstye.main.UserInput.askPressEnter;

/**
 * Players model object.
 * Contains all the players playing the game
 */
class Players
{
    private Player players[];
    private int numberOfPlayers;

    /**
     * Create the new players.
     */
    Players()
    {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

        System.out.println("Please enter the number of players");
        numberOfPlayers = askPositiveNumber();

        players = new Player[numberOfPlayers];
        for (int i = 0; i < players.length; i++)
        {
            System.out.println("Player " + (i + 1) + ", please enter your name:");
            System.out.print("> ");
            String name = scanner.nextLine();

            players[i] = new Player(name);
        }
    }

    /**
     * Let the players play the game until the scoreGrid is not full, show the winner/or all the players that have a draw.
     */
    void playGame()
    {

        int turnNumber = 1;

        // While the last player can still play
        while (!players[players.length - 1].getScoreGrid().isFull())
        {
            turnPlayers(turnNumber);
            turnNumber++;
        }
        displayScores();
        displayResults();
        records();
    }

    /**
     * Displays the score of every player.
     */
    private void displayScores()
    {
        for (int i = 0; i < players.length; i++)
        {
            System.out.println("Player " + (i + 1) + ": " + players[i].getName() + " got " + players[i].totalScore() + " points.");
        }
    }

    /**
     * Playing one turn for each player.
     *
     * @param turnNumber the turn we're at - one game has 13 turns
     */
    private void turnPlayers(int turnNumber)
    {

        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            System.out.println("\n==== TURN " + turnNumber + " ====\n");
            System.out.println("Player n°" + (playerNumber + 1) + ": it's " + player.getName() + "'s turn!");
            player.playOneTurn(playerNumber);
            if (players.length == 1)
            {
                pressEnterToContinue(player);
            }
            //if there are multiple players, do not ask to press something before next turn
            else
            {
                pressEnterToContinue((playerNumber + 1) % players.length, players[(playerNumber + 1) % players.length]);
            }


            ScoreGrid scoreGrid = player.getScoreGrid();
            if (scoreGrid.isFull())
            {
                System.out.println("\n\n=== FINAL RESULTS FOR PLAYER n°" + (playerNumber + 1) + ": " + player.getName() + " ===\n");
                System.out.println(scoreGrid.getDisplay(null, player.getName()));
                System.out.println("You scored a total of " + scoreGrid.getTotalScore() + " points!\n");
            }
        }
    }

    /**
     * <strong style='color:red'>UNDER DEVELOPMENT, DO NOT USE.</strong>
     *
     * <p>
     * Let one player play one turn.
     *
     * @param player       the player
     * @param playerNumber the number of the player
     */
    private void onePlayerTurn(Player player, int playerNumber)
    {
        System.out.println("Player n°" + (playerNumber + 1) + ": it's " + player.getName() + "'s turn!");
        player.playOneTurn(playerNumber);
    }

    /**
     * Get the player that won the Yahtzee game, having the most points at the end.
     *
     * @return the winner of the game
     */
    private int getWinner()
    {
        int max = 0;
        int winner = 0;
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (max < player.totalScore())
            {
                max = player.totalScore();
                winner = playerNumber;
            }
        }
        return winner;
    }

    /**
     * In case of a draw, find all the players that have a draw.
     *
     * @return all the players that are winners
     */
    private ArrayList<Integer> drawPlayers()
    {

        ArrayList<Integer> drawPlayers = new ArrayList<>();
        drawPlayers.add(getWinner());
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (playerNumber != getWinner() && player.totalScore() == players[getWinner()].totalScore())
            {
                drawPlayers.add(playerNumber);
            }
        }
        return drawPlayers;
    }

    /**
     * Find out if there's a draw in the game.
     *
     * @return true if there's a draw, false otherwise
     */
    private boolean isDraw()
    {
        boolean isDraw = false;
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (playerNumber != getWinner() && player.totalScore() == players[getWinner()].totalScore())
            {
                isDraw = true;
            }
        }
        return isDraw;
    }

    /**
     * Display the results in the given stream.
     *
     * @param out the stream where the results will be displayed
     */
    private void displayResults(PrintStream out)
    {
        // If there is a draw, display all winners
        if (isDraw())
        {
            ArrayList<Integer> drawPlayers = drawPlayers();
            for (int i = 0; i < drawPlayers.size(); i++)
            {
                Integer player = drawPlayers.get(i);
                if (i != 0)
                {
                    out.print(" and ");
                }
                out.print(players[player].getName());
            }
            out.println(" have a draw!");
        }
        else
        {
            out.println(players[getWinner()].getName() + " has won!\n");
        }
    }

    /**
     * Display the results in the console.
     */
    private void displayResults()
    {
        displayResults(System.out);
    }


    /**
     * Register the records after each game.
     */
    private void records()
    {
        FileOutputStream records_ = null;
        PrintStream records;
        try
        {
            records_ = new FileOutputStream("records.txt", true);
        }
        catch (Exception ignored)
        {
            System.exit(0);
        }

        records = new PrintStream(records_);
        records.print("GAME ");
        // write the number of players in the game
        records.print(players.length + " player(s) : ");

        //write the names of the players
        for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
        {
            Player player = players[playerNumber];
            if (playerNumber != 0)
            {
                records.print(", ");
            }
            records.print(player.getName());
        }
        records.println();

        for (Player player : players)
        {
            ScoreGrid scoreGrid = player.getScoreGrid();
            records.println("\t" + player.getName() + ": " + scoreGrid.getTotalScore());
        }

        displayResults(records);
        records.println();
        records.close();
    }


    /**
     * Return the number of the current game according to {@code records.txt}.
     *
     * @return the number of the current game
     */
    static int getCurrentGameNumber()
    {
        File file = new File("records.txt");
        Scanner inputFile = null;
        int number = 1;

        try
        {
            inputFile = new Scanner(file, StandardCharsets.UTF_8.name());
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("The records.txt file was not found");
            System.exit(0);
        }

        String text = inputFile.useDelimiter("\\Z").next();
        Pattern pattern = Pattern.compile("[\\r\\n]+GAME (\\d+)");
        Matcher m = pattern.matcher(text);

        while (m.find())
        {
            number = Integer.parseInt(m.group(1)) + 1;
        }
        inputFile.close();
        return number;
    }

    /**
     * Wait for the player to press Enter, in order to get to the next turn.
     * Works better than {@link #pressEnterToContinue(Player)} for multi player.
     *
     * @param playerNumber the number of the player
     * @param player       the player
     */
    private static void pressEnterToContinue(int playerNumber, Player player)

    {
        System.out.println("Player n°" + (playerNumber + 1) + ": " + player.getName() + ", it's your turn ! Tap anything when you're ready...");
        askPressEnter();
    }

    /**
     * Wait for the player to press Enter, in order to get to the next turn.
     * Works better than {@link #pressEnterToContinue(int, Player)} for single player.
     *
     * @param player the player
     */
    private static void pressEnterToContinue(Player player)
    {
        System.out.println(player.getName() + ", press Enter when you're ready for next turn!...");
        askPressEnter();
    }
}




