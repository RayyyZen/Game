package com.app;

/**
 * The player class that contains his name, the number of hearts he has, his current score, his spawn and current coordinates on a level's grid and the total number of players that were created
 * @version 3.0 (Third world)
 * @since 1.0
 * @author Rayane
 */
public class Player {

    /**
     * The name of the player
     */
    private final String name;

    /**
     * The number of hearts that the player has
     */
    private int numberOfHearts;

    /**
     * The current score of the player (it is always a positive integer)
     */
    private int score;

    /**
     * The current coordinates of the player on a level's grid
     */
    private Coordinates coordinates;

    /**
     * The starting coordinates of the player on a level's grid
     */
    private Coordinates spawnCoordinates;

    /**
     * The number of players that were created
     */
    private static int numberOfPlayers = 0;

    /**
     * The number of hearts that each player initially has
     */
    private static final int HEARTS = 5;

    /**
     * The player constructor that takes as an argument only a name
     * @param name The name of the player
     */
    public Player(String name){
        this.name = name;
        this.numberOfHearts = HEARTS;
        this.score = 0;
        this.coordinates = null;
        this.spawnCoordinates = null;
        //The player isn't initially on any level
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
     * Returns the number of hearts that the player has
     * @return The number of hearts that the player has
     */
    public int getNumberOfHearts(){
        return this.numberOfHearts;
    }

    /**
     * Returns the score of the player
     * @return The score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Checks if some coordinates given as an argument are null
     * @param coordinates The coordinates that will be checked
     */
    private void checkCoordinates(Coordinates coordinates){
        if(coordinates == null){
            throw new IllegalStateException("The player's coordinates are null");
        }
    }

    /**
     * Returns the current coordinates line of the player on a level's grid
     * @return The current coordinates line of the player on a level's grid
     */
    public int getCurrentLine(){
        checkCoordinates(this.coordinates);
        return this.coordinates.getLine();
    }

    /**
     * Returns the current coordinates column of the player on a level's grid
     * @return The current coordinates column of the player on a level's grid
     */
    public int getCurrentColumn(){
        checkCoordinates(this.coordinates);
        return this.coordinates.getColumn();
    }

    /**
     * Returns the starting line coordinates of the player on a level's grid
     * @return The starting line coordinates of the player on a level's grid
     */
    public int getSpawnLine(){
        checkCoordinates(this.spawnCoordinates);
        return this.spawnCoordinates.getLine();
    }

    /**
     * Returns the starting column coordinates of the player on a level's grid
     * @return The starting column coordinates of the player on a level's grid
     */
    public int getSpawnColumn(){
        checkCoordinates(this.spawnCoordinates);
        return this.spawnCoordinates.getColumn();
    }

    /**
     * Returns the number of players created
     * @return The number of players created
     */
    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Modify a number by adding or deducting from him a specific value
     * @param number The number that will be modified
     * @param modify The value that will be added or deducted from the number
     * @return The modified number
     */
    private int modifyNumber(int number, int modify){
        number += modify;
        if(number < 0){
            number = 0;
        }

        return number;
    }

    /**
     * Either adds or removes a specific number of hearts from the player
     * If the number of hearts is positive it will be added to the player and if it is negative it will be removed from him
     * @param numberOfHearts The number of hearts that will be added to or removed from the player, depending on its sign
     */
    public void modifyNumberOfHearts(int numberOfHearts){
        this.numberOfHearts = modifyNumber(this.numberOfHearts,numberOfHearts);
    }

    /**
     * Either adds or substracts a score from the player's current score
     * If the score is positive it will be added to the player and if it is negative it will be deducted from him
     * @param score The score that will be added to or deducted from the player's score, depending on its sign
     */
    public void modifyScore(int score){
        this.score = modifyNumber(this.score,score);
    }

    /**
     * Modify any coordinates and returns it
     * @param line Specific line on a level's grid
     * @param column Specific column on a level's grid
     * @return The modified coordinates
     */
    private Coordinates modifyAnyCoordinates(Coordinates coordinates, int line, int column){
        if(coordinates == null){
            return new Coordinates(line,column);

        } else {
            coordinates.setLine(line);
            coordinates.setColumn(column);
            return coordinates;
        }
    }

    /**
     * Modify the current coordinates of the player on a level's grid
     * @param line Specific line on a level's grid
     * @param column Specific column on a level's grid
     */
    public void setCoordinates(int line, int column){
        this.coordinates = modifyAnyCoordinates(this.coordinates,line,column);
    }

    /**
     * Modify the starting coordinates of the player on a level's grid
     * @param line Specific line on a level's grid
     * @param column Specific column on a level's grid
     */
    public void setSpawnCoordinates(int line, int column){
        this.spawnCoordinates = modifyAnyCoordinates(this.spawnCoordinates,line,column);
    }

    /**
     * Returns a String that contains the player's name and score with a certain format
     * @return A String that contains the name and the score of the player
     */
    @Override
    public String toString(){
        String stringHeart = "heart", stringScore = "pt";

        if(this.numberOfHearts > 1){
            stringHeart += "s";
        }

        if(this.score > 1){
            stringScore += "s";
        }

        String str = this.name + " : " + this.numberOfHearts + " " + stringHeart + " left | " + this.score + " " + stringScore + "\n";

        if(this.coordinates != null){
            return  str + this.coordinates;

        } else {
            return str + "The player isn't located on any level yet";
        }
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
