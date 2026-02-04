package com.app;

import java.util.Scanner;

/**
 * The main class
 * @version 1.1 (First world)
 * @since 1.0
 * @author Rayane
 */
public class Main {
    
    /**
     * The main method
     * @param args An array of String arguments passed into the main method from the input of the application
     */
    public static void main(String args[]){

        if(args.length != 1){
            throw new IllegalArgumentException("There are no file name given as an argument");
        }

        Player player = new Player();
        Level level = new Level(args[0],player);
        Scanner scanner = new Scanner(System.in);
        char inputDirection;
        String leave = "Ll";

        do{

            System.out.print("\033[2J\033[H\033[?25l");
            System.out.println(level);

            View.displayControls(scanner);

            inputDirection = scanner.next().charAt(0);
            level.move(View.charToDirection(inputDirection));
            
        }while(!leave.contains("" + inputDirection));

        System.out.println("\nYou left the game\n");

        scanner.close();
    }
}