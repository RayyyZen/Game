package com.app.entity;

import com.app.cell.Cell;
import com.app.cell.Coordinates;

public abstract class Entity {
    
    /**
     * The name of the player
     */
    private final String name;

    /**
     * The number of hearts that the player has
     */
    private int numberOfHearts;

    /**
     * The current coordinates of the player on a level's grid
     */
    private Coordinates coordinates;

    /**
     * The starting coordinates of the player on a level's grid
     */
    private Coordinates spawnCoordinates;

    public Entity(String name, int numberOfHearts){
        this.name = name;
        this.numberOfHearts = numberOfHearts;
        this.coordinates = null;
        this.spawnCoordinates = null;
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
     * Modify a number by adding or deducting from him a specific value
     * @param number The number that will be modified
     * @param modify The value that will be added or deducted from the number
     * @return The modified number
     */
    protected int modifyNumber(int number, int modify){
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

    public abstract boolean validMovement(Cell cell);

    /**
     * Returns a String that contains the player's name and score with a certain format
     * @return A String that contains the name and the score of the player
     */
    @Override
    public String toString(){
        String stringHeart = "heart";

        if(this.numberOfHearts > 1){
            stringHeart += "s";
        }

        String str = this.name + " : " + this.numberOfHearts + " " + stringHeart + " left\n";

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
        if(obj == null || !(obj instanceof Entity)){
            return false;
        
        } else if(obj == this) {
            return true;
        }

        Entity mobile = (Entity)obj;
        return this.name.toLowerCase().equals(mobile.getName().toLowerCase());
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    public abstract String getSymbol();
}
