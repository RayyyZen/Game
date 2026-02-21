package com.app;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main class
 * @version 3.0 (Third world)
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

        if(args.length == 0){
            View.displayAndExit("No arguments ! You must provide as an argument the paths of the files that will be used to create the levels", 2);
        }

        int numberOfLevels = args.length;

        Scanner scanner = new Scanner(System.in);
        //The class that will be used to get the user's inputs

        String input = null;
        //The user's input

        System.out.print("\n--> Enter your username : ");
        String name = scanner.nextLine();

        do{

            Player player = new Player(name);
            Level level = null;

            int index = -1;

            do{

                if(index == -1 || (index < numberOfLevels - 1 && level.getNumberOfCoins() < 1)){
                    try{
                        index++;
                        level = new Level(args[index],player);
                    }
                    catch(IOException e){
                        View.displayAndExit("The file's content can't be accessed", 3);
                    }
                    catch(IllegalArgumentException e){
                        View.displayAndExit(e.getMessage(), 4);
                    } 
                }

                if(index == numberOfLevels - 1 && level.getNumberOfCoins() < 1){
                    break;
                }

                View.displayScreen(level);

                System.out.print("-> Choose  : ");
                input = scanner.nextLine();
                //To get the user's input

                level.move(View.inputToDirection(input));
                level.effect();

            }while(!View.endGame(input) && player.getNumberOfHearts() > 0);

            View.displayEndScreen(level);

            if(player.getNumberOfHearts() > 0 || input.toLowerCase().equals("l")){
                //It means the player has finished the levels or he wants to leave
                break;
            }

            System.out.print("--> Press 'r' to restart or anything else to leave : ");
            input = scanner.nextLine();

        }while(input.toLowerCase().equals("r"));

        scanner.close();
    }
}