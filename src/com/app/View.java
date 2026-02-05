package com.app;

/**
 * The class that manages the user's inputs and the displays
 * @version 2.0 (Second world)
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
     * Converts a character to an actual direction that will be taken by the player
     * @param car The character that symbolizes a certain direction
     * @return The direction that will be taken by the player
     */
    public static Direction charToDirection(char car){
        switch(car){
            case 'z' :
            case 'Z' :
                return Direction.UP;

            case 's' :
            case 'S' :
                return Direction.DOWN;

            case 'q' :
            case 'Q' :
                return Direction.LEFT;

            case 'd' :
            case 'D' :
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
        System.out.print("-> Choose  : ");
    }

    /**
     * Checks if the user left the game according to his input
     * @param car The first letter of the input's user
     * @return true if the input refers to a control that allows the user to leave the game, or false otherwise
     */
    public static boolean endGame(char car){
        String leave = "Ll";
        //The games controls that can be used to leave the game

        return leave.contains("" + car);
    }
}