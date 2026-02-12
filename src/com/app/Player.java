package com.app;

/**
 * The player class that contains his name, his current score and the total number of players that were created
 * @version 2.1 (Second world)
 * @since 1.0
 * @author Rayane
 */
public class Player {

    /**
     * The name of the player
     */
    private final String name;

    /**
     * The current score of the player (it is always a positive integer)
     */
    private int score;

    /**
     * The number of players that were created
     */
    private static int numberOfPlayers = 0;

    /**
     * The player constructor that takes as an argument only a name
     * @param name The name of the player
     */
    public Player(String name){
        this.name = name;
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
     * Returns the name of the player
     * @return The name of the player
     */
    public String getName(){
        return this.name;
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
        this.score += score;
        if(this.score < 0){
            this.score = 0;
        }
    }

    /**
     * Returns a String that contains the player's name and score with a certain format
     * @return A String that contains the name and the score of the player
     */
    @Override
    public String toString(){
        String str = this.name + " : " + this.score + " pt";
        if(this.score > 1){
            str += "s";
        }
        return str;
    }

    /**
     * Checks if a player is equal to an object
     * @param obj The object that will be compared to the player
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Player)){
            return false;
        
        } else if(obj == this) {
            return true;
        }

        Player player = (Player)obj;
        return this.name.toLowerCase().equals(player.getName().toLowerCase());
    }
}
