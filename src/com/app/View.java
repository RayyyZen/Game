package com.app;

import java.util.Scanner;

public class View {

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
    
    public static void displayControls(Scanner sc){
        System.out.println("-> Game controls : ");
        System.out.println("----> 'z' Or 'Z' : Up");
        System.out.println("----> 's' Or 'S' : Down");
        System.out.println("----> 'q' Or 'Q' : Left");
        System.out.println("----> 'd' Or 'D' : Right\n");
        System.out.println("----> 'l' Or 'L' : Leave\n");
        System.out.print("-> Choose  : ");
    }
}
