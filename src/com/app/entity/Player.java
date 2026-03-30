package com.app.entity;

import com.app.cell.Cell;
import com.app.cell.CellType;

/**
 * The player class that contains his attributes and the total number of players that were created
 * @version 4.0 (Fourth world)
 * @since 1.0 (First world)
 * @author Rayane
 */
public class Player extends Entity {

    /**
     * The current score of the player (it is always a positive integer)
     */
    private int score;

    /**
     * The number of players that were created
     */
    private static int numberOfPlayers = 0;

    /**
     * The number of hearts that each player initially has
     */
    private static final int HEARTS = 5;

    /**
     * The emoji that represents the players
     */
    private static final String SYMBOL = "🐱‍👤";

    /**
     * The player constructor that takes as an argument only a name
     * @param name The name of the player
     */
    public Player(String name){
        super(name,HEARTS,SYMBOL);
        this.score = 0;
        numberOfPlayers++;
    }

    /**
     * The player constructor that doesn't take any arguments and automatically generates his name
     */
    public Player(){
        this("Player" + (numberOfPlayers + 1));
    }

    /**
     * Returns the score of the player
     * @return The score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Returns the number of players created
     * @return The number of players created
     */
    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Either adds or substracts a score from the player's current score
     * If the score is positive it will be added to the player and if it is negative it will be deducted from him
     * @param score The score that will be added to or deducted from the player's score, depending on its sign
     */
    public void modifyScore(int score){
        this.score = super.modifyNumber(this.score,score);
    }

    /**
     * Checks if a cell is valid according to the player's possible movements
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the player, or false otherwise
     */
    @Override
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY || type == CellType.TRAP;
    }

    /**
     * Returns a String that contains the player's attributes with a certain format
     * @return A String that contains the attributs of the player
     */
    @Override
    public String toString(){
        String stringScore = "pt";

        if(this.score > 1){
            stringScore += "s";
        }

        return super.toString() + "\nScore : " + this.score + " " + stringScore;
    }
}