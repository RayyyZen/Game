package com.app;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main class
 * @version 2.1 (Second world)
 * @since 1.0
 * @author Rayane
 */
public class Main {
    
    /**
     * The main method
     * @param args An array of String arguments passed into the main method from the input of the application
     * args[0] must contain the name of a compliant file that will be used to create the level 
     */
    public static void main(String args[]){

        if(args.length != 1){
            View.displayAndExit("Invalid arguments ! You must provide as an argument the path of a file that will be used to create the level", 2);
        }

        Player player = new Player();
        Level level = null;

        try{
            level = new Level(args[0],player);
        }
        catch(IOException e){
            View.displayAndExit("The file's content can't be accessed", 3);
        }
        catch(IllegalArgumentException e){
            View.displayAndExit(e.getMessage(), 4);
        }

        Scanner scanner = new Scanner(System.in);
        //The class that will be used to get the user's inputs

        String input;
        //The user's input

        do{

            System.out.print("\033[H\033[2J\033[3J");
            // \033[H : moves the cursor to the top left
            // \033[2J : erase screen
            // \033[3J : erase saved lines

            System.out.println(level);

            View.displayControls();

            input = scanner.nextLine();
            //To get the user's input

            level.move(View.inputToDirection(input));
            
        }while(!View.endGame(input));

        System.out.println("\nYou left the game !\n");

        scanner.close();
    }
}