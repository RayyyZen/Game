package com.app.display;

import java.util.List;

import com.app.level.Direction;
import com.app.level.Level;

/**
 * The class that manages the user's inputs and the displays
 * @version 3.0 (Third world)
 * @since 2.0
 * @author Rayane
 */
public class View {

    /**
     * Displays an error message and exits the program
     * @param message The message that will be displayed according to the error that had occured
     * @param exitNumber An exit number that characterizes the specific error
     */
    public static void displayAndExit(String message, int exitNumber){
        System.err.println(message);
        System.exit(exitNumber);
    }

    /**
     * Converts an input into an actual direction that will be taken by the player
     * @param str The user's input that symbolizes a certain direction
     * @return The direction that will be taken by the player
     */
    public static Direction inputToDirection(String str){
        switch(str.toLowerCase()){
            case "z" :
                return Direction.UP;

            case "s" :
                return Direction.DOWN;

            case "q" :
                return Direction.LEFT;

            case "d" :
                return Direction.RIGHT;

            default :
                return Direction.NONE;
        }
    }
    
    /**
     * Displays the game controls : 
     * The controls that allows the player to move in a specific direction
     * The controls that allows the player to leave the game
     */
    public static void displayControls(){
        System.out.println("-> Game controls : ");
        System.out.println("----> 'z' Or 'Z' : Up");
        System.out.println("----> 's' Or 'S' : Down");
        System.out.println("----> 'q' Or 'Q' : Left");
        System.out.println("----> 'd' Or 'D' : Right\n");
        System.out.println("----> 'l' Or 'L' : Leave\n");
    }

    private static void displayEnnemies(List<String> names){
        if(names != null && !names.isEmpty()){
            System.out.println("-> You were hitted by : \n");
            for(String enemyName : names){
                System.out.println("--> " + enemyName);
            }
            System.out.println("");
        }
    }

    /**
     * Display all the elements needed on the screen (The grid, the controls and the player's attributs)
     * @param level The level which is played by the player
     */
    public static void displayScreen(Level level, List<String> names){
        System.out.print("\033[H\033[2J\033[3J");
        // \033[H : moves the cursor to the top left
        // \033[2J : erase screen
        // \033[3J : erase saved lines

        System.out.println(level);

        displayEnnemies(names);

        displayControls();
    }

    /**
     * Display the end screen
     * @param level The level which is played by the player
     */
    public static void displayEndScreen(Level level, List<String> names){
        View.displayScreen(level,names);

        if(level.gameOver()){
            System.out.println("-> Game over !\n");

        } else if(level.getNumberOfCoins() <= 0) {
            System.out.println("-> You finished the game ! Thank you for playing !\n");
            
        } else {
            System.out.println("-> You left the game\n");
        }
        
    }

    /**
     * Checks if the user left the game according to his input
     * @param str The user's input
     * @return true if the input refers to a control that allows the user to leave the game, or false otherwise
     */
    public static boolean endGame(String str){
        return str.toLowerCase().equals("l");
    }
}