package com.app.model.display;

import java.util.Iterator;
import java.util.List;

import com.app.model.entity.Player;
import com.app.model.level.Direction;
import com.app.model.level.Level;

/**
 * The class that manages the user's inputs and the displays
 * @version 5.0 (Fifth world)
 * @since 2.0 (Second world)
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

    /**
     * Display all the enemies that collided with the player
     * @param collidedEnemies The attributes of the enemies that collided with the player
     */
    private static void displayCollidedEnemies(List<String> collidedEnemies){
        if(collidedEnemies != null && !collidedEnemies.isEmpty()){
            String string = collidedEnemies.size() == 1 ? "enemy" : "enemies";
            System.out.println("-> You collided with the following " + string + " : \n");
            for(String enemyName : collidedEnemies){
                System.out.println("--> " + enemyName + "\n");
            }
        }
    }

    /**
     * Display the skills that the player unlocked
     * @param newSkills The new skills that were unlocked by the player
     */
    private static void displayUnlockedSkills(List<String> newSkills){
        if(newSkills != null && !newSkills.isEmpty()){
            String s = newSkills.size() == 1 ? "" : "s";
            System.out.print("-> You unlocked the following skill" + s + " : ");

            Iterator<String> iterator = newSkills.iterator();

            while(iterator.hasNext()){
                String skill = iterator.next();
                System.out.print(skill);
                if(iterator.hasNext()){
                    System.out.print(", ");
                }
            }

            System.out.println("\n");
        }
    }

    /**
     * Display all the elements needed on the screen (The grid, the controls, the player's attributs, and eventually the enemies that collided with him and the new skills that he unlocked)
     * @param level The level which is played by the player
     * @param collidedEnemies The enemies that collided with the player
     * @param newSkills The new skills that the player unlocked
     */
    public static void displayScreen(Level level, List<String> collidedEnemies, List<String> newSkills){
        System.out.print("\033[H\033[2J\033[3J");
        // \033[H : moves the cursor to the top left
        // \033[2J : erase screen
        // \033[3J : erase saved lines

        System.out.println(level);
        displayUnlockedSkills(newSkills);
        displayCollidedEnemies(collidedEnemies);
        displayControls();
    }

    /**
     * Display the end screen
     * @param level The level which is played by the player
     * @param collidedEnemies The enemies that collided with the player
     * @param newSkills The new skills that the player unlocked
     */
    public static void displayEndScreen(Level level, List<String> collidedEnemies, List<String> newSkills){
        View.displayScreen(level,collidedEnemies,newSkills);

        if(level.gameOver()){
            System.out.println("-> Game over !\n");

        } else if(level.win()) {
            System.out.println("-> You finished the game ! Thank you for playing !\n");
            
        } else {
            System.out.println("-> You left the game\n");
        }
        
    }

    /**
     * Checks if the user's input corresponds to an inventory index
     * @param player The player located on the level's grid
     * @param input The user's input
     * @return True if the user's input corresponds to an inventory index, or false otherwise
     */
    public static boolean validInventoryIndex(Player player, String input){
        int inventorySize = player.getInventorySize();

        for(int i = 1; i <= inventorySize; i++){
            if(("" + i).equals(input)){
                return true;
            }
        }

        return false;
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