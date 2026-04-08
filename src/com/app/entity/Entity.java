package com.app.entity;

import com.app.cell.Cell;
import com.app.cell.Coordinates;

/**
 * The entity class that contains his name, the number of hearts he has, his spawn and current coordinates on a level's grid
 * @version 5.0 (Fifth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
public abstract class Entity {
    
    /**
     * The name of the entity
     */
    private final String name;

    /**
     * The number of hearts that the entity has
     */
    private int numberOfHearts;

    /**
     * The emoji symbol that represents the entity
     */
    private final String symbol;

    /**
     * The current coordinates of the entity on a level's grid
     */
    private Coordinates coordinates;

    /**
     * The starting coordinates of the entity on a level's grid
     */
    private Coordinates spawnCoordinates;

    /**
     * The entity constructor that takes as an argument a name, a number of hearts and an emoji symbol
     * @param name The name of the entity
     * @param numberOfHearts The initial number of hearts of the entity
     * @param symbol The emoji symbol that represents the entity
     */
    protected Entity(String name, int numberOfHearts, String symbol){
        this.name = name;
        this.numberOfHearts = numberOfHearts;
        this.coordinates = null;
        this.spawnCoordinates = null;
        this.symbol = symbol;
    }

    /**
     * Returns the name of the entity
     * @return The name of the entity
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the number of hearts that the entity has
     * @return The number of hearts that the entity has
     */
    public int getNumberOfHearts(){
        return this.numberOfHearts;
    }

    /**
     * Returns the emoji symbol of the entity
     * @return the emoji symbol of the entity
     */
    public String getSymbol(){
        return this.symbol;
    }

    /**
     * Checks if some coordinates given as an argument are null
     * @param coordinates The coordinates that will be checked
     */
    private void checkCoordinates(Coordinates coordinates){
        if(coordinates == null){
            throw new IllegalStateException("The entity's coordinates are null");
        }
    }

    /**
     * Returns the current coordinates line of the entity on a level's grid
     * @return The current coordinates line of the entity on a level's grid
     */
    public int getCurrentLine(){
        checkCoordinates(this.coordinates);
        return this.coordinates.getLine();
    }

    /**
     * Returns the current coordinates column of the entity on a level's grid
     * @return The current coordinates column of the entity on a level's grid
     */
    public int getCurrentColumn(){
        checkCoordinates(this.coordinates);
        return this.coordinates.getColumn();
    }

    /**
     * Returns the starting line coordinates of the entity on a level's grid
     * @return The starting line coordinates of the entity on a level's grid
     */
    public int getSpawnLine(){
        checkCoordinates(this.spawnCoordinates);
        return this.spawnCoordinates.getLine();
    }

    /**
     * Returns the starting column coordinates of the entity on a level's grid
     * @return The starting column coordinates of the entity on a level's grid
     */
    public int getSpawnColumn(){
        checkCoordinates(this.spawnCoordinates);
        return this.spawnCoordinates.getColumn();
    }

    /**
     * Checks if 2 entities coordinates are equal
     * @param entity The entity that will be compared
     * @return True if the entities coordinates are equal, or false otherwise
     */
    public boolean sameCoordinates(Entity entity){
        return this.coordinates != null && entity != null && this.coordinates.equals(entity.coordinates);
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
     * Either adds or removes a specific number of hearts from the entity
     * If the number of hearts is positive it will be added to the entity and if it is negative it will be removed from him
     * @param numberOfHearts The number of hearts that will be added to or removed from the entity, depending on its sign
     */
    public void modifyNumberOfHearts(int numberOfHearts){
        this.numberOfHearts = modifyNumber(this.numberOfHearts,numberOfHearts);
    }

    /**
     * Modify any coordinates and returns it
     * @param coordinates The coordinates that will be modified
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
     * Modify the current coordinates of the entity on a level's grid
     * @param line Specific line on a level's grid
     * @param column Specific column on a level's grid
     */
    public void setCoordinates(int line, int column){
        this.coordinates = modifyAnyCoordinates(this.coordinates,line,column);
    }

    /**
     * Modify the starting coordinates of the entity on a level's grid
     * @param line Specific line on a level's grid
     * @param column Specific column on a level's grid
     */
    public void setSpawnCoordinates(int line, int column){
        this.spawnCoordinates = modifyAnyCoordinates(this.spawnCoordinates,line,column);
    }

    /**
     * Checks if a cell is valid according to the entity's possible movements
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the entity, or false otherwise
     */
    public abstract boolean validMovement(Cell cell);

    /**
     * Returns a String that contains the entity's name, number of hearts and coordinates with a certain format
     * @return A String that contains the name, the number of hearts and the coordinates of the entity
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
            return str + "Not located on any level yet";
        }
    }

    /**
     * Checks if an entity is equal to an object
     * @param obj The object that will be compared to the entity
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Entity)){
            return false;
        
        } else if(obj == this) {
            return true;
        }

        Entity entity = (Entity)obj;
        return this.name.toLowerCase().equals(entity.name.toLowerCase());
    }

    /**
     * Returns the hash code of the entity
     * @return the hash code of the entity
     */
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}