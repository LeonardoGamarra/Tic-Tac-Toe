/**
 * This code plays a game of Tic-Tac-Toe
 * @author Leonardo Gamarra
 * @version 1.0
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)throws Exception
    {
        Scanner in = new Scanner(System.in);
        
        // Generates game map
        char[][] gameMap = new char[3][3];
        
        // Generates game map number positions
        int[][] numberMap = new int[3][3];
        int counter = 1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                numberMap[i][j] = counter;
                counter++;
            }
        }
        
        // Chooses which player will start playing
        int playerStart = (int) Math.ceil(Math.random() * 2);
        int playerRound = playerStart;
        
        // Creates a variable to store the status of the game
        boolean gameFinished = false;
        
        // Display intro messages
        System.out.println("Welcome to Tic-Tac-Toe");
        System.out.println("You are playing as: X");
        System.out.print("Would you like to see the placing scheme? (Y / N): ");
        char tutorial = in.next().charAt(0);
        if (Character.toUpperCase(tutorial) == 'Y')
        {
            printTutorial();
            Thread.sleep(4000);
        }
        
        // Main gameplay loop
        int round = 1;
        int lastPlay = 0;
        int lastCpuPlay = 0;
        do
        {
            boolean validPlace = false;
            if (playerRound == 1)
            {
                // Player's turn
                System.out.println();
                System.out.println("Choose a location to place your X:");
                printMap(gameMap);
                int place;
                do
                {
                    place = in.nextInt();
                    validPlace = checkPlace(place, gameMap);
                    if (!validPlace)
                        System.out.print("This space has already been used! Enter again: ");
                }
                while (!validPlace);
                lastPlay = place;
                int[] coords = placeConverter(place);
                gameMap[coords[0]][coords[1]] = 'X';
                System.out.println();
                System.out.println("Your play:");
                Thread.sleep(1000);
                printMap(gameMap);
            }
            if (playerRound == 2)
            {
                // CPU's turn
                System.out.println();
                System.out.println("CPU's playing");
                Thread.sleep(1000);
                int place = 0;
                int[] coords = {0,0};
                if (round == 1)
                {
                    place = 5;
                    lastCpuPlay = place;
                    coords = placeConverter(place);
                }
                else
                {
                    coords = cpuPlay(gameMap, playerStart, lastPlay, lastCpuPlay);
                    place = numberMap[coords[0]][coords[1]];
                    validPlace = checkPlace(place, gameMap);
                    while (!validPlace)
                    {
                        place = (int) Math.ceil(Math.random() * 9);
                        validPlace = checkPlace(place, gameMap);
                    }
                    lastCpuPlay = place;
                    coords = placeConverter(place);
                }
                gameMap[coords[0]][coords[1]] = 'O';
                printMap(gameMap);
            }
            if (playerRound == 1)
                playerRound = 2;
            else
                playerRound = 1;
            gameFinished = gameEnded(gameMap, round);
            round++;
        }
        while (!gameFinished);
    }
    /**
     * Prints game map
     * @param Receives the game map as a bidimensional array
     * @return Doesn`t return any values, just prints the game map
     */
    public static void printMap(char[][] map)
    {
        System.out.println();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (map[i][j] != '\u0000')
                    System.out.print(" " + map[i][j] + " ");
                else
                    System.out.print("   ");
                if (j != 2)
                    System.out.print("|");
            }
            System.out.println();
            if (i != 2)
                System.out.println("-----------");
        }
    }
    public static void printTutorial()
    {
        System.out.println();
        System.out.println("This is the game placement system:");
        System.out.println();
        int counter = 1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                System.out.print(" " + counter + " ");
                if (j != 2)
                    System.out.print("|");
                counter++;
            }
            System.out.println();
            if (i != 2)
                System.out.println("-----------");
        }
        System.out.println();
        System.out.println("To choose a place you enter its number.");
    }
    public static boolean checkPlace(int in, char[][] map)
    {
        int[] coords = placeConverter(in);
        
        if (map[coords[0]][coords[1]] == '\u0000')
            return true;
        else
            return false;
    }
    public static int[] placeConverter(int in)
    {
        int[] coords = new int[2];
        switch (in)
        {
            case 1:
                coords[0] = 0;
                coords[1] = 0;
                break;
            case 2:
                coords[0] = 0;
                coords[1] = 1;
                break;
            case 3:
                coords[0] = 0;
                coords[1] = 2;
                break;
            case 4:
                coords[0] = 1;
                coords[1] = 0;
                break;
            case 5:
                coords[0] = 1;
                coords[1] = 1;
                break;
            case 6:
                coords[0] = 1;
                coords[1] = 2;
                break;
            case 7:
                coords[0] = 2;
                coords[1] = 0;
                break;
            case 8:
                coords[0] = 2;
                coords[1] = 1;
                break;
            case 9:
                coords[0] = 2;
                coords[1] = 2;
                break;
            default:
                coords[0] = 0;
                coords[1] = 0;
                break;
        }
        return coords;
    }
    public static ArrayList<Integer> findPlayer(char[][] map)
    {
        ArrayList<Integer> locations = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (map[i][j] == 'X')
                {
                    locations.add(i);
                    locations.add(j);
                }
            }
        }
        return locations;
    }
    public static int[] cpuPlay(char[][] gameMap, int playerStart, int lastPlay, int lastCpuPlay)
    {
        int possiblePlay[] = new int[2];
        char locate = 'F';
        int play = 0;
        for (int e = 0; e < 2; e++)
        {
            if (e == 0)
            {
                locate = 'O';
                play = lastCpuPlay;
            }
            else
            {
                locate = 'X';
                play = lastPlay;
            }
            int total = 0;
            int coords[] = placeConverter(play);
            // Checking horizontal plays
            for (int i = 0; i <= 2; i++)
            {
                if (gameMap[coords[0]][i] == locate)
                {
                    total++;
                }
                else
                {
                    possiblePlay[0] = coords[0];
                    possiblePlay[1] = i;
                }
            }
            if (total == 2 && gameMap[possiblePlay[0]][possiblePlay[1]] == '\u0000')
                return possiblePlay;
            // Checking vertical plays
            total = 0;
            for (int i = 0; i <= 2; i++)
            {
                if (gameMap[i][coords[1]] == locate)
                {
                    total++;
                }
                else
                {
                    possiblePlay[0] = i;
                    possiblePlay[1] = coords[1];
                }
            }
            if (total == 2 && gameMap[possiblePlay[0]][possiblePlay[1]] == '\u0000')
                return possiblePlay;
            // Checking upleft-downright plays
            total = 0;
            for (int i = 0; i <= 2; i++)
            {
                if (gameMap[i][i] == locate)
                {
                    total++;
                }
                else
                {
                    possiblePlay[0] = i;
                    possiblePlay[1] = i;
                }
            }
            if (total == 2 && gameMap[possiblePlay[0]][possiblePlay[1]] == '\u0000')
                return possiblePlay;
            // Checking upright-downleft plays
            total = 0;
            for (int i = 3; i <= 7; i += 2)
            {
                coords = placeConverter(i);
                if (gameMap[coords[0]][coords[1]] == locate)
                {
                    total++;
                }
                else
                {
                    possiblePlay[0] = coords[0];
                    possiblePlay[1] = coords[1];
                }
            }
            if (total == 2 && gameMap[possiblePlay[0]][possiblePlay[1]] == '\u0000')
                return possiblePlay;
        }
        return possiblePlay;
    }
    public static boolean gameEnded(char[][] gameMap, int round)
    {
        boolean ended = false;
        boolean roundLimit = false;
        char locate = 'F';
        for (int i = 0; i < 2; i++)
        {
            if (ended)
                break;
            if (i == 0)
                locate = 'X';
            else
                locate = 'O';
            // Checking horizontal plays
            for (int j = 0; j <= 2; j++)
            {
                int total = 0;
                for (int k = 0; k <= 2; k++)
                {
                    if (gameMap[j][k] == locate)
                    {
                        total++;
                    }
                }
                if (total == 3)
                {
                    ended = true;
                    break;
                }
            }
            if (ended)
                break;
            // Checking vertical plays
            for (int j = 0; j <= 2; j++)
            {
                int total = 0;
                for (int k = 0; k <= 2; k++)
                {
                    if (gameMap[k][j] == locate)
                    {
                        total++;
                    }
                }
                if (total == 3)
                {
                    ended = true;
                    break;
                }
            }
            if (ended)
                break;
            // Checking upleft-downright plays
            int total = 0;
            for (int j = 0; j <= 2; j++)
            {
                if (gameMap[j][j] == locate)
                {
                    total++;
                }
                if (total == 3)
                {
                    ended = true;
                    break;
                }
            }
            if (ended)
                break;
            // Checking upright-downleft plays
            total = 0;
            for (int j = 3; j <= 7; j += 2)
            {
                int coords[] = placeConverter(j);
                if (gameMap[coords[0]][coords[1]] == locate)
                {
                    total++;
                }
                if (total == 3)
                {
                    ended = true;
                    break;
                }
            }
            if (ended)
                break;
            if (round == 9)
            {
                ended = true;
                roundLimit = true;
                break;
            }
        }
        if (ended && locate == 'X' && !roundLimit)
        {
            System.out.println();
            System.out.println("YOU WON THE GAME!");
        }
        else if (ended && locate == 'O' && !roundLimit)
        {
            System.out.println();
            System.out.println("THE CPU WON THE GAME!");
        }
        else if (roundLimit)
        {
            System.out.println();
            System.out.println("THE GAME TIED!");
        }
        return ended;
    }
}